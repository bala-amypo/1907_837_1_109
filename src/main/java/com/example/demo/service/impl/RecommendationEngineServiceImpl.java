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

    @Override
    public List<RecommendationRecord> getRecommendation(Long userId) {
        return repo.findByUserId(userId);
    }
}
