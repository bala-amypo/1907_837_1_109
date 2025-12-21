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

    // -------------------------------------------------------
    // 1️⃣ Create Rule
    // -------------------------------------------------------
    @Override
    public RewardRule createRule(RewardRule rule) {
        return rewardRuleRepository.save(rule);
    }

    // -------------------------------------------------------
    // 2️⃣ Get Rule by ID
    // -------------------------------------------------------
    @Override
    public RewardRule getRuleById(Long id) {
        return rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found"));
    }

    // -------------------------------------------------------
    // 3️⃣ Get All Rules
    // -------------------------------------------------------
    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }

    // -------------------------------------------------------
    // 4️⃣ Update Rule
    // -------------------------------------------------------
    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {

        RewardRule existing = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found"));

        existing.setCardId(updated.getCardId());
        existing.setCategory(updated.getCategory());
        existing.setActive(updated.isActive());

        existing.setRuleName(updated.getRuleName());
        existing.setRewardPoints(updated.getRewardPoints());
        existing.setDescription(updated.getDescription());

        existing.setMerchant(updated.getMerchant());
        existing.setBonusPoints(updated.getBonusPoints());

        return rewardRuleRepository.save(existing);
    }

    // -------------------------------------------------------
    // 5️⃣ Delete Rule
    // -------------------------------------------------------
    @Override
    public void deleteRule(Long id) {

        RewardRule rule = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward Rule not found"));

        rewardRuleRepository.delete(rule);
    }
}
