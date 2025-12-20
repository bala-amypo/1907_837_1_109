package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService engineService;

    public RecommendationController(RecommendationEngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/{userId}")
    public List<RecommendationRecord> generate(@PathVariable Long userId) {
        return engineService.generateRecommendation(userId);
    }

    @GetMapping
    public List<RecommendationRecord> getAll() {
        return engineService.getAllRecommendations();
    }
}
