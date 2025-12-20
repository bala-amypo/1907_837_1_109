package com.example.demo.service.impl;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.repository.RecommendationRecordRepository;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final RecommendationRecordRepository repo;

    public RecommendationEngineServiceImpl(RecommendationRecordRepository repo) {
        this.repo = repo;
    }

    /**
     * Generate recommendations for a given user.
     * For now, simply fetches all existing recommendations for that user.
     */
    @Override
    public List<RecommendationRecord> generateRecommendation(Long userId) {
        return repo.findByUserId(userId);
    }

    /**
     * Get all recommendations stored in the system.
     */
    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return repo.findAll();
    }
}
