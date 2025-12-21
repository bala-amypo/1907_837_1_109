package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.entity.CreditCardRecord;
import com.example.demo.entity.RewardRule;
import com.example.demo.entity.RecommendationRecord;

import com.example.demo.exception.ResourceNotFoundException;

import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.repository.RecommendationRecordRepository;

import com.example.demo.service.RecommendationEngineService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepo;
    private final UserProfileRepository userRepo;
    private final CreditCardRecordRepository cardRepo;
    private final RewardRuleRepository ruleRepo;
    private final RecommendationRecordRepository recRepo;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepo,
            UserProfileRepository userRepo,
            CreditCardRecordRepository cardRepo,
            RewardRuleRepository ruleRepo,
            RecommendationRecordRepository recRepo
    ) {
        this.intentRepo = intentRepo;
        this.userRepo = userRepo;
        this.cardRepo = cardRepo;
        this.ruleRepo = ruleRepo;
        this.recRepo = recRepo;
    }

    /**
     * Generate recommendation based on purchase intent.
     */
    @Override
    public List<RecommendationRecord> generateRecommendation(Long intentId) {

        // 1️⃣ Fetch intent
        PurchaseIntentRecord intent = intentRepo.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        Long userId = intent.getUserId();
        String category = intent.getCategory();
        double amount = intent.getAmount();

        // 2️⃣ Fetch user's active cards
        List<CreditCardRecord> activeCards = cardRepo.findActiveCardsByUser(userId);

        if (activeCards.isEmpty()) {
            throw new ResourceNotFoundException("User has no active cards");
        }

        double bestReward = -1;
        Long bestCardId = null;
        String calcJson = "";

        // 3️⃣ Evaluate each card based on reward rules
        for (CreditCardRecord card : activeCards) {

            List<RewardRule> rules = ruleRepo.findActiveRulesForCardCategory(card.getId(), category);

            for (RewardRule rule : rules) {

                double reward = amount * rule.getMultiplier(); // multiplier added in entity

                if (reward > bestReward) {
                    bestReward = reward;
                    bestCardId = card.getId();

                    calcJson = "{ \"cardId\": " + card.getId() +
                            ", \"ruleId\": " + rule.getId() +
                            ", \"reward\": " + reward +
                            "}";
                }
            }
        }

        if (bestCardId == null) {
            throw new ResourceNotFoundException("No valid reward rules found for this category");
        }

        // 4️⃣ Save recommendation result
        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(userId);
        rec.setPurchaseIntentId(intentId);
        rec.setRecommendedCardId(bestCardId);
        rec.setExpectedRewardValue(bestReward);
        rec.setCalculationDetailsJson(calcJson);
        rec.setRecommendedAt(LocalDateTime.now());

        recRepo.save(rec);

        // Return as list (because interface expects List)
        return List.of(rec);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recRepo.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}
