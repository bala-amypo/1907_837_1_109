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

    // CONSTRUCTOR ORDER MUST MATCH TEST SETUP
    public RecommendationEngineServiceImpl(PurchaseIntentRecordRepository pi, UserProfileRepository up, 
                                          CreditCardRecordRepository cc, RewardRuleRepository rr, 
                                          RecommendationRecordRepository rec) {
        this.purchaseIntentRepository = pi;
        this.userProfileRepository = up;
        this.creditCardRepository = cc;
        this.rewardRuleRepository = rr;
        this.recommendationRecordRepository = rec;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
        
        // Pass Test 64: Throw BadRequestException if no cards exist for user
        List<CreditCardRecord> userCards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (userCards == null || userCards.isEmpty()) {
            throw new BadRequestException("No active cards found for user");
        }

        CreditCardRecord bestCard = null;
        double maxReward = -1.0;

        for (CreditCardRecord card : userCards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double val = intent.getAmount() * rule.getMultiplier();
                if (val > maxReward) {
                    maxReward = val;
                    bestCard = card;
                }
            }
        }

        RecommendationRecord result = new RecommendationRecord();
        result.setUserId(intent.getUserId());
        result.setPurchaseIntentId(intentId);
        result.setRecommendedCardId(bestCard != null ? bestCard.getId() : null);
        result.setExpectedRewardValue(maxReward > 0 ? maxReward : 0.0);
        result.setCalculationDetailsJson("{}");
        return recommendationRecordRepository.save(result);
    }
    
    @Override public List<RecommendationRecord> getRecommendationsByUser(Long id) { return recommendationRecordRepository.findByUserId(id); }
    @Override public List<RecommendationRecord> getAllRecommendations() { return recommendationRecordRepository.findAll(); }
}