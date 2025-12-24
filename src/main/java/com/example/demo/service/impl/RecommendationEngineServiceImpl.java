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
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        // 1. Fetch Intent
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        // 2. Fetch User
        UserProfile user = userProfileRepository.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 3. Fetch Active Cards (This triggers Test 64)
        List<CreditCardRecord> activeCards = creditCardRepository.findActiveCardsByUser(user.getId());
        
        // TEST 64 REQUIREMENT: Throw BadRequestException if no cards found
        if (activeCards == null || activeCards.isEmpty()) {
            throw new BadRequestException("User has no active credit cards");
        }

        CreditCardRecord bestCard = null;
        double maxRewardValue = -1.0;

        // 4. Calculate best reward
        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            
            for (RewardRule rule : rules) {
                double currentReward = intent.getAmount() * rule.getMultiplier();
                if (currentReward > maxRewardValue) {
                    maxRewardValue = currentReward;
                    bestCard = card;
                }
            }
        }

        // If cards exist but no rules match, you might still want a default or an error
        if (bestCard == null) {
            throw new BadRequestException("No reward rules found for the purchase category");
        }

        // 5. Save and Return Recommendation (Test 63 Happy Path)
        RecommendationRecord recommendation = new RecommendationRecord();
        recommendation.setUserId(user.getId());
        recommendation.setPurchaseIntentId(intentId);
        recommendation.setRecommendedCardId(bestCard.getId());
        recommendation.setExpectedRewardValue(maxRewardValue);
        recommendation.setCalculationDetailsJson("{\"category\":\"" + intent.getCategory() + "\"}");
        
        return recommendationRecordRepository.save(recommendation);
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