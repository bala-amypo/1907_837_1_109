// package com.example.demo.controller;

// import com.example.demo.entity.RecommendationRecord;
// import com.example.demo.service.RecommendationEngineService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/recommendations")
// public class RecommendationController {

//     private final RecommendationEngineService engineService;

//     public RecommendationController(RecommendationEngineService engineService) {
//         this.engineService = engineService;
//     }

//     @GetMapping("/{userId}")
//     public List<RecommendationRecord> generate(@PathVariable Long userId) {
//         return engineService.generateRecommendation(userId);
//     }

//     @GetMapping
//     public List<RecommendationRecord> getAll() {
//         return engineService.getAllRecommendations();
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@SecurityRequirement(name = "JWT")
@Tag(name = "Recommendations", description = "Recommendation management endpoints")
public class RecommendationController {
    
    private final RecommendationEngineService recommendationEngineService;

    public RecommendationController(RecommendationEngineService recommendationEngineService) {
        this.recommendationEngineService = recommendationEngineService;
    }

    @PostMapping("/generate/{intentId}")
    @Operation(summary = "Generate recommendation for purchase intent")
    public ResponseEntity<RecommendationRecord> generateRecommendation(@PathVariable Long intentId) {
        return ResponseEntity.ok(recommendationEngineService.generateRecommendation(intentId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get recommendations by user")
    public ResponseEntity<List<RecommendationRecord>> getRecommendationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationEngineService.getRecommendationsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recommendation by ID")
    public ResponseEntity<RecommendationRecord> getRecommendationById(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationEngineService.getRecommendationById(id));
    }

    @GetMapping
    @Operation(summary = "Get all recommendations")
    public ResponseEntity<List<RecommendationRecord>> getAllRecommendations() {
        return ResponseEntity.ok(recommendationEngineService.getAllRecommendations());
    }
}