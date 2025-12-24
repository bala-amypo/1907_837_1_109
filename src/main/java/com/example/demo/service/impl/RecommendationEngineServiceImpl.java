package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
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