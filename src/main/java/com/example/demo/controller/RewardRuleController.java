package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    @Autowired
    private RewardRuleService ruleService;

    @PostMapping
    public RewardRule create(@RequestBody RewardRule rule) {
        return ruleService.createRule(rule);
    }

    @GetMapping("/{id}")
    public RewardRule getById(@PathVariable Long id) {
        return ruleService.getRule(id);
    }

    @GetMapping
    public List<RewardRule> getAll() {
        return ruleService.getAllRules();
    }

    @PutMapping("/{id}")
    public RewardRule update(@PathVariable Long id, @RequestBody RewardRule rule) {
        return ruleService.updateRule(id, rule);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return "Rule deleted successfully";
    }
}
