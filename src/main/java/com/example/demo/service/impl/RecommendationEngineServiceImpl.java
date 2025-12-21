package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final CreditCardRecordRepository cardRepo;
    private final PurchaseIntentRecordRepository intentRepo;
    private final RecommendationRecordRepository recRepo;

    public RecommendationEngineServiceImpl(CreditCardRecordRepository cardRepo,
                                           PurchaseIntentRecordRepository intentRepo,
                                           RecommendationRecordRepository recRepo) {
        this.cardRepo = cardRepo;
        this.intentRepo = intentRepo;
        this.recRepo = recRepo;
    }

    @Override
    public List<RecommendationRecord> generateRecommendation(Long userId) {

        List<PurchaseIntentRecord> intents = intentRepo.findByUserId(userId);
        List<CreditCardRecord> cards = cardRepo.findAll();

        List<RecommendationRecord> finalRecs = new ArrayList<>();

        for (PurchaseIntentRecord intent : intents) {
            String category = intent.getCategory();

            for (CreditCardRecord card : cards) {
                if (category.equalsIgnoreCase(card.getCategory())) {

                    RecommendationRecord rec = new RecommendationRecord();
                    rec.setUserId(userId);
                    rec.setCardId(card.getId());
                    rec.setCardName(card.getCardName());
                    rec.setCategory(category);
                    rec.setTotalPoints(card.getRewardPoints());

                    recRepo.save(rec);
                    finalRecs.add(rec);
                }
            }
        }

        return finalRecs;
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}
