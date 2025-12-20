package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reward-rules")
public class RewardRuleController {

    @Autowired
    private RewardRuleService ruleService;

    @PostMapping
    public RewardRule create(@RequestBody RewardRule rule) {
        return ruleService.create(rule);
    }

    @GetMapping("/{id}")
    public RewardRule get(@PathVariable Long id) {
        return ruleService.getById(id);
    }

    @GetMapping
    public List<RewardRule> getAll() {
        return ruleService.getAll();
    }

    @PutMapping("/{id}")
    public RewardRule update(@PathVariable Long id, @RequestBody RewardRule rule) {
        return ruleService.update(id, rule);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        ruleService.delete(id);
        return "Rule deleted";
    }
}
