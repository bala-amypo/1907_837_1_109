// package com.example.demo.controller;

// import com.example.demo.entity.RewardRule;
// import com.example.demo.service.RewardRuleService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/api/reward-rules")
// public class RewardRuleController {
//     private final RewardRuleService rewardRuleService;
//     public RewardRuleController(RewardRuleService rewardRuleService) { this.rewardRuleService = rewardRuleService; }
    
//     @PostMapping
//     public ResponseEntity<RewardRule> createRule(@RequestBody RewardRule rule) { return ResponseEntity.ok(rewardRuleService.createRule(rule)); }
//     @GetMapping("/active")
//     public ResponseEntity<List<RewardRule>> getActiveRules() { return ResponseEntity.ok(rewardRuleService.getActiveRules()); }
//     @GetMapping
//     public ResponseEntity<List<RewardRule>> getAllRules() { return ResponseEntity.ok(rewardRuleService.getAllRules()); }
// }

package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    private final RewardRuleService ruleService;

    public RewardRuleController(RewardRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<RewardRule> createRule(@RequestBody RewardRule rule) {
        return ResponseEntity.ok(ruleService.createRule(rule));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RewardRule>> getActiveRules() {
        return ResponseEntity.ok(ruleService.getActiveRules());
    }

    @GetMapping
    public ResponseEntity<List<RewardRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }
}