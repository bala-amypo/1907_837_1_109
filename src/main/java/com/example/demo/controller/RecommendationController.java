package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationEngineService engineService;

    @PostMapping("/generate/{userId}")
    public RecommendationRecord generate(@PathVariable Long userId) {
        return engineService.generateRecommendation(userId);
    }

    @GetMapping("/{id}")
    public RecommendationRecord get(@PathVariable Long id) {
        return engineService.getRecommendation(id);
    }

    @GetMapping
    public List<RecommendationRecord> getAll() {
        return engineService.getAllRecommendations();
    }
}
