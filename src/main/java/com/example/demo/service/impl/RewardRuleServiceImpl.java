package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    @Autowired
    private RewardRuleRepository rewardRuleRepository;

    @Override
    public RewardRule createRule(RewardRule rule) {
        return rewardRuleRepository.save(rule);
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }

    @Override
    public RewardRule getRule(Long id) {
        return rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found with ID: " + id));
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updatedRule) {
        RewardRule existing = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found with ID: " + id));

        existing.setRuleName(updatedRule.getRuleName());
        existing.setRewardPoints(updatedRule.getRewardPoints());
        existing.setCategory(updatedRule.getCategory());
        existing.setMerchant(updatedRule.getMerchant());
        existing.setBonusPoints(updatedRule.getBonusPoints());
        existing.setActive(updatedRule.isActive());

        return rewardRuleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        RewardRule rule = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found with ID: " + id));
        rewardRuleRepository.delete(rule);
    }
}
