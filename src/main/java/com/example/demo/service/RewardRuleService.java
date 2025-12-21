package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {

    RewardRule createRule(RewardRule rule);

    RewardRule getRule(Long id);

    List<RewardRule> getAllRules();

    RewardRule updateRule(Long id, RewardRule updated);

    void deleteRule(Long id);
}
