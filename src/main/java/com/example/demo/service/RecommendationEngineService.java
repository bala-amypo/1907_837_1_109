package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationEngineService {

    List<RecommendationRecord> generateRecommendation(Long userId);

    List<RecommendationRecord> getAllRecommendations();
}
