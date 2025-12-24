package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {
    RewardRule createRule(RewardRule rule);
    RewardRule updateRule(Long id, RewardRule updated);
    List<RewardRule> getRulesByCard(Long cardId);
    List<RewardRule> getActiveRules();
    List<RewardRule> getAllRules();
}