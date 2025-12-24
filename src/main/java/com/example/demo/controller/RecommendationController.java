// package com.example.demo.controller;

// import com.example.demo.entity.RecommendationRecord;
// import com.example.demo.service.RecommendationEngineService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/api/recommendations")
// public class RecommendationController {
//     private final RecommendationEngineService recommendationEngineService;
//     public RecommendationController(RecommendationEngineService recommendationEngineService) { this.recommendationEngineService = recommendationEngineService; }
    
//     @PostMapping("/generate/{intentId}")
//     public ResponseEntity<RecommendationRecord> generateRecommendation(@PathVariable Long intentId) { return ResponseEntity.ok(recommendationEngineService.generateRecommendation(intentId)); }
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<RecommendationRecord>> getRecommendationsByUser(@PathVariable Long userId) { return ResponseEntity.ok(recommendationEngineService.getRecommendationsByUser(userId)); }
//     @GetMapping
//     public ResponseEntity<List<RecommendationRecord>> getAllRecommendations() { return ResponseEntity.ok(recommendationEngineService.getAllRecommendations()); }
// }

package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService recommendationService;

    public RecommendationController(RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate/{intentId}")
    public ResponseEntity<RecommendationRecord> generate(@PathVariable Long intentId) {
        return ResponseEntity.ok(recommendationService.generateRecommendation(intentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationRecord>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<RecommendationRecord>> getAll() {
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }
}