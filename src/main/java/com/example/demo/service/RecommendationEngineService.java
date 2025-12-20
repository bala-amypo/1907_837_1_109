package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationEngineService {

    /**
     * Generate recommendations for a specific user.
     */
    List<RecommendationRecord> generateRecommendation(Long userId);

    /**
     * Fetch all recommendations stored in the system.
     */
    List<RecommendationRecord> getAllRecommendations();
}
