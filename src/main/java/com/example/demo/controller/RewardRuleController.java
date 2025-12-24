// package com.example.demo.controller;

// import com.example.demo.entity.RewardRule;
// import com.example.demo.service.RewardRuleService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/reward-rules")
// public class RewardRuleController {

//     @Autowired
//     private RewardRuleService rewardRuleService;

//     @PostMapping
//     public ResponseEntity<RewardRule> createRule(@RequestBody RewardRule rule) {
//         return ResponseEntity.ok(rewardRuleService.createRule(rule));
//     }

//     @GetMapping
//     public ResponseEntity<List<RewardRule>> getAllRules() {
//         return ResponseEntity.ok(rewardRuleService.getAllRules());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<RewardRule> getRule(@PathVariable Long id) {
//         return ResponseEntity.ok(rewardRuleService.getRule(id));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<RewardRule> updateRule(@PathVariable Long id,
//                                                  @RequestBody RewardRule updated) {
//         return ResponseEntity.ok(rewardRuleService.updateRule(id, updated));
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deleteRule(@PathVariable Long id) {
//         rewardRuleService.deleteRule(id);
//         return ResponseEntity.ok("Rule deleted successfully");
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
@SecurityRequirement(name = "JWT")
@Tag(name = "Reward Rules", description = "Reward rule management endpoints")
public class RewardRuleController {
    
    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @PostMapping
    @Operation(summary = "Create a new reward rule")
    public ResponseEntity<RewardRule> createRule(@Valid @RequestBody RewardRule rule) {
        return ResponseEntity.ok(rewardRuleService.createRule(rule));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reward rule")
    public ResponseEntity<RewardRule> updateRule(@PathVariable Long id, @Valid @RequestBody RewardRule rule) {
        return ResponseEntity.ok(rewardRuleService.updateRule(id, rule));
    }

    @GetMapping("/card/{cardId}")
    @Operation(summary = "Get rules by card")
    public ResponseEntity<List<RewardRule>> getRulesByCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(rewardRuleService.getRulesByCard(cardId));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active rules")
    public ResponseEntity<List<RewardRule>> getActiveRules() {
        return ResponseEntity.ok(rewardRuleService.getActiveRules());
    }

    @GetMapping
    @Operation(summary = "Get all rules")
    public ResponseEntity<List<RewardRule>> getAllRules() {
        return ResponseEntity.ok(rewardRuleService.getAllRules());
    }
}