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
    public RewardRule getRule(Long id) {
        return rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {

        RewardRule existing = getRule(id);

        existing.setRuleName(updated.getRuleName());
        existing.setRewardPoints(updated.getRewardPoints());
        existing.setCategory(updated.getCategory());
        existing.setMerchant(updated.getMerchant());
        existing.setBonusPoints(updated.getBonusPoints());
        existing.setActive(updated.isActive());

        return rewardRuleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        RewardRule rule = getRule(id);
        rewardRuleRepository.delete(rule);
    }
}
