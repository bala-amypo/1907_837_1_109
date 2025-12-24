// package com.example.demo.service.impl;

// import com.example.demo.entity.PurchaseIntentRecord;
// import com.example.demo.entity.CreditCardRecord;
// import com.example.demo.entity.RewardRule;
// import com.example.demo.entity.RecommendationRecord;

// import com.example.demo.exception.ResourceNotFoundException;

// import com.example.demo.repository.PurchaseIntentRecordRepository;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.repository.CreditCardRecordRepository;
// import com.example.demo.repository.RewardRuleRepository;
// import com.example.demo.repository.RecommendationRecordRepository;

// import com.example.demo.service.RecommendationEngineService;

// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class RecommendationEngineServiceImpl implements RecommendationEngineService {

//     private final PurchaseIntentRecordRepository intentRepo;
//     private final UserProfileRepository userRepo;
//     private final CreditCardRecordRepository cardRepo;
//     private final RewardRuleRepository ruleRepo;
//     private final RecommendationRecordRepository recRepo;

//     public RecommendationEngineServiceImpl(
//             PurchaseIntentRecordRepository intentRepo,
//             UserProfileRepository userRepo,
//             CreditCardRecordRepository cardRepo,
//             RewardRuleRepository ruleRepo,
//             RecommendationRecordRepository recRepo
//     ) {
//         this.intentRepo = intentRepo;
//         this.userRepo = userRepo;
//         this.cardRepo = cardRepo;
//         this.ruleRepo = ruleRepo;
//         this.recRepo = recRepo;
//     }

//     /**
//      * Generate recommendation based on purchase intent.
//      */
//     @Override
//     public List<RecommendationRecord> generateRecommendation(Long intentId) {

//         // 1️⃣ Fetch intent
//         PurchaseIntentRecord intent = intentRepo.findById(intentId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

//         Long userId = intent.getUserId();
//         String category = intent.getCategory();
//         double amount = intent.getAmount();

//         // 2️⃣ Fetch user's active cards
//         List<CreditCardRecord> activeCards = cardRepo.findActiveCardsByUser(userId);

//         if (activeCards.isEmpty()) {
//             throw new ResourceNotFoundException("User has no active cards");
//         }

//         double bestReward = -1;
//         Long bestCardId = null;
//         String calcJson = "";

//         // 3️⃣ Evaluate each card based on reward rules
//         for (CreditCardRecord card : activeCards) {

//             List<RewardRule> rules = ruleRepo.findActiveRulesForCardCategory(card.getId(), category);

//             for (RewardRule rule : rules) {

//                 double reward = amount * rule.getMultiplier(); // multiplier added in entity

//                 if (reward > bestReward) {
//                     bestReward = reward;
//                     bestCardId = card.getId();

//                     calcJson = "{ \"cardId\": " + card.getId() +
//                             ", \"ruleId\": " + rule.getId() +
//                             ", \"reward\": " + reward +
//                             "}";
//                 }
//             }
//         }

//         if (bestCardId == null) {
//             throw new ResourceNotFoundException("No valid reward rules found for this category");
//         }

//         // 4️⃣ Save recommendation result
//         RecommendationRecord rec = new RecommendationRecord();
//         rec.setUserId(userId);
//         rec.setPurchaseIntentId(intentId);
//         rec.setRecommendedCardId(bestCardId);
//         rec.setExpectedRewardValue(bestReward);
//         rec.setCalculationDetailsJson(calcJson);
//         rec.setRecommendedAt(LocalDateTime.now());

//         recRepo.save(rec);

//         // Return as list (because interface expects List)
//         return List.of(rec);
//     }

//     @Override
//     public RecommendationRecord getRecommendationById(Long id) {
//         return recRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
//     }

//     @Override
//     public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
//         return recRepo.findByUserId(userId);
//     }

//     @Override
//     public List<RecommendationRecord> getAllRecommendations() {
//         return recRepo.findAll();
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    
    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRepository;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository purchaseIntentRepository,
            UserProfileRepository userProfileRepository,
            CreditCardRecordRepository creditCardRepository,
            RewardRuleRepository rewardRuleRepository,
            RecommendationRecordRepository recommendationRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
        this.userProfileRepository = userProfileRepository;
        this.creditCardRepository = creditCardRepository;
        this.rewardRuleRepository = rewardRuleRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
            .orElseThrow(() -> new ResourceNotFoundException("Purchase intent not found with id: " + intentId));
        
        UserProfile user = userProfileRepository.findById(intent.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (!user.getActive()) {
            throw new BadRequestException("User account is not active");
        }
        
        List<CreditCardRecord> activeCards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (activeCards.isEmpty()) {
            throw new BadRequestException("No active cards available for user");
        }
        
        CreditCardRecord bestCard = null;
        double maxReward = 0.0;
        
        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();
                if (reward > maxReward) {
                    maxReward = reward;
                    bestCard = card;
                }
            }
        }
        
        if (bestCard == null) {
            throw new BadRequestException("No applicable reward rules found");
        }
        
        String calculationDetails = String.format(
            "{\"cardId\":%d,\"amount\":%.2f,\"multiplier\":%.2f,\"reward\":%.2f}",
            bestCard.getId(), intent.getAmount(), maxReward / intent.getAmount(), maxReward
        );
        
        RecommendationRecord recommendation = new RecommendationRecord(
            intent.getUserId(),
            intentId,
            bestCard.getId(),
            maxReward,
            calculationDetails
        );
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recommendationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
}