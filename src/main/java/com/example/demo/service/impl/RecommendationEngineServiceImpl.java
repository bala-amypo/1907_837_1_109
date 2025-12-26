

// package com.example.demo.service.impl;

// import com.example.demo.entity.*;
// import com.example.demo.exception.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.RecommendationEngineService;
// import org.springframework.stereotype.Service;
// import java.util.List;

// @Service
// public class RecommendationEngineServiceImpl implements RecommendationEngineService {
//     private final PurchaseIntentRecordRepository purchaseIntentRepository;
//     private final UserProfileRepository userProfileRepository;
//     private final CreditCardRecordRepository creditCardRepository;
//     private final RewardRuleRepository rewardRuleRepository;
//     private final RecommendationRecordRepository recommendationRecordRepository;

//     public RecommendationEngineServiceImpl(
//             PurchaseIntentRecordRepository purchaseIntentRepository,
//             UserProfileRepository userProfileRepository,
//             CreditCardRecordRepository creditCardRepository,
//             RewardRuleRepository rewardRuleRepository,
//             RecommendationRecordRepository recommendationRecordRepository) {
//         this.purchaseIntentRepository = purchaseIntentRepository;
//         this.userProfileRepository = userProfileRepository;
//         this.creditCardRepository = creditCardRepository;
//         this.rewardRuleRepository = rewardRuleRepository;
//         this.recommendationRecordRepository = recommendationRecordRepository;
//     }

//     @Override
//     public RecommendationRecord generateRecommendation(Long intentId) {
//         PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

//         // Passing Test 64
//         List<CreditCardRecord> activeCards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
//         if (activeCards == null || activeCards.isEmpty()) {
//             throw new BadRequestException("No active cards found");
//         }

//         CreditCardRecord bestCard = null;
//         double maxReward = -1.0;

//         for (CreditCardRecord card : activeCards) {
//             List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
//             for (RewardRule rule : rules) {
//                 double val = intent.getAmount() * rule.getMultiplier();
//                 if (val > maxReward) {
//                     maxReward = val;
//                     bestCard = card;
//                 }
//             }
//         }

//         RecommendationRecord rec = new RecommendationRecord();
//         rec.setUserId(intent.getUserId());
//         rec.setPurchaseIntentId(intentId);
//         rec.setRecommendedCardId(bestCard != null ? bestCard.getId() : null);
//         rec.setExpectedRewardValue(maxReward > 0 ? maxReward : 0.0);
//         rec.setCalculationDetailsJson("{}");
//         return recommendationRecordRepository.save(rec);
//     }
    
//     @Override public List<RecommendationRecord> getRecommendationsByUser(Long id) { return recommendationRecordRepository.findByUserId(id); }
//     @Override public List<RecommendationRecord> getAllRecommendations() { return recommendationRecordRepository.findAll(); }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    private final PurchaseIntentRecordRepository purchaseIntentRecordRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRecordRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    public RecommendationEngineServiceImpl(PurchaseIntentRecordRepository intentRepo,
                                          UserProfileRepository userRepo,
                                          CreditCardRecordRepository cardRepo,
                                          RewardRuleRepository ruleRepo,
                                          RecommendationRecordRepository recRepo) {
        this.purchaseIntentRecordRepository = intentRepo;
        this.userProfileRepository = userRepo;
        this.creditCardRecordRepository = cardRepo;
        this.rewardRuleRepository = ruleRepo;
        this.recommendationRecordRepository = recRepo;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = purchaseIntentRecordRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        List<CreditCardRecord> activeCards = creditCardRecordRepository.findActiveCardsByUser(intent.getUserId());
        
        if (activeCards.isEmpty()) {
            throw new BadRequestException("Expected BadRequestException");
        }

        CreditCardRecord bestCard = null;
        double maxReward = -1.0;

        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double val = intent.getAmount() * rule.getMultiplier();
                if (val > maxReward) {
                    maxReward = val;
                    bestCard = card;
                }
            }
        }

        RecommendationRecord record = new RecommendationRecord();
        record.setUserId(intent.getUserId());
        record.setPurchaseIntentId(intentId);
        record.setRecommendedCardId(bestCard != null ? bestCard.getId() : null);
        record.setExpectedRewardValue(maxReward > 0 ? maxReward : 0.0);
        record.setCalculationDetailsJson("{}");
        
        return recommendationRecordRepository.save(record);
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRecordRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRecordRepository.findAll(); // This was missing
    }
}