package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    @Autowired
    private RewardRuleService rewardRuleService;

    @PostMapping
    public ResponseEntity<RewardRule> createRule(@RequestBody RewardRule rule) {
        return ResponseEntity.ok(rewardRuleService.createRule(rule));
    }

    @GetMapping
    public ResponseEntity<List<RewardRule>> getAllRules() {
        return ResponseEntity.ok(rewardRuleService.getAllRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardRule> getRule(@PathVariable Long id) {
        return ResponseEntity.ok(rewardRuleService.getRule(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardRule> updateRule(@PathVariable Long id,
                                                 @RequestBody RewardRule updated) {
        return ResponseEntity.ok(rewardRuleService.updateRule(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRule(@PathVariable Long id) {
        rewardRuleService.deleteRule(id);
        return ResponseEntity.ok("Rule deleted successfully");
    }
}
