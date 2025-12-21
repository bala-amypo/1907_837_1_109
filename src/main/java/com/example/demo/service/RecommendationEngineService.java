package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;

import java.util.List;

public interface RecommendationEngineService {

    // Generate recommendation from intent
    List<RecommendationRecord> generateRecommendation(Long intentId);

    // Get single recommendation
    RecommendationRecord getRecommendationById(Long id);

    // Get user recommendations
    List<RecommendationRecord> getRecommendationsByUser(Long userId);

    // Get all recommendations
    List<RecommendationRecord> getAllRecommendations();
}
