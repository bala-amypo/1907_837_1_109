package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository repo;

    public RewardRuleServiceImpl(RewardRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public RewardRule create(RewardRule rule) {
        return repo.save(rule);
    }

    @Override
    public RewardRule getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public List<RewardRule> getAll() {
        return repo.findAll();
    }

    @Override
    public RewardRule update(Long id, RewardRule updated) {
        RewardRule r = getById(id);

        r.setCategory(updated.getCategory());
        r.setMerchant(updated.getMerchant());
        r.setBonusPoints(updated.getBonusPoints());

        return repo.save(r);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
