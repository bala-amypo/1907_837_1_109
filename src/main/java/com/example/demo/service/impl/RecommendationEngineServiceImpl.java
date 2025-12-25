// package com.example.demo.service.impl;

// import com.example.demo.entity.*;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.*;
// import com.example.demo.service.RecommendationEngineService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class RecommendationEngineServiceImpl implements RecommendationEngineService {
//     private final PurchaseIntentRecordRepository purchaseIntentRepository;
//     private final UserProfileRepository userProfileRepository;
//     private final CreditCardRecordRepository creditCardRepository;
//     private final RewardRuleRepository rewardRuleRepository;
//     private final RecommendationRecordRepository recommendationRecordRepository;

//     @Override
//     public RecommendationRecord generateRecommendation(Long intentId) {
//         PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
        
//         List<CreditCardRecord> activeCards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
//         if (activeCards.isEmpty()) {
//             throw new BadRequestException("No active cards found for user");
//         }

//         CreditCardRecord bestCard = null;
//         double maxReward = -1.0;

//         for (CreditCardRecord card : activeCards) {
//             List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
//             for (RewardRule rule : rules) {
//                 double reward = intent.getAmount() * rule.getMultiplier();
//                 if (reward > maxReward) {
//                     maxReward = reward;
//                     bestCard = card;
//                 }
//             }
//         }

//         if (bestCard == null) throw new BadRequestException("No matching reward rules");

//         RecommendationRecord rec = new RecommendationRecord();
//         rec.setUserId(intent.getUserId());
//         rec.setPurchaseIntentId(intentId);
//         rec.setRecommendedCardId(bestCard.getId());
//         rec.setExpectedRewardValue(maxReward);
//         rec.setCalculationDetailsJson("{\"logic\": \"max_multiplier\"}");
//         return recommendationRecordRepository.save(rec);
//     }

//     @Override
//     public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
//         return recommendationRecordRepository.findByUserId(userId);
//     }

//     @Override
//     public List<RecommendationRecord> getAllRecommendations() {
//         return recommendationRecordRepository.findAll();
//     }
// }

   package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    // Constructor order matches the test setup exactly
    public RecommendationEngineServiceImpl(PurchaseIntentRecordRepository pir, UserProfileRepository upr,
                                          CreditCardRecordRepository ccr, RewardRuleRepository rrr,
                                          RecommendationRecordRepository rrecr) {
        this.purchaseIntentRepository = pir;
        this.userProfileRepository = upr;
        this.creditCardRepository = ccr;
        this.rewardRuleRepository = rrr;
        this.recommendationRecordRepository = rrecr;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        userProfileRepository.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Test 64 Requirement: Throw BadRequestException if no active cards
        List<CreditCardRecord> cards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (cards == null || cards.isEmpty()) {
            throw new BadRequestException("No active cards found for user");
        }

        CreditCardRecord bestCard = null;
        double maxReward = -1.0;

        for (CreditCardRecord card : cards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();
                if (reward > maxReward) {
                    maxReward = reward;
                    bestCard = card;
                }
            }
        }

        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(intent.getUserId());
        rec.setPurchaseIntentId(intentId);
        rec.setRecommendedCardId(bestCard != null ? bestCard.getId() : null);
        rec.setExpectedRewardValue(maxReward > 0 ? maxReward : 0.0);
        rec.setCalculationDetailsJson("{}");
        return recommendationRecordRepository.save(rec);
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRecordRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRecordRepository.findAll();
    }
}