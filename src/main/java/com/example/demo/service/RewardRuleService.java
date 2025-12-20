package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {

    RewardRule create(RewardRule rule);

    RewardRule getById(Long id);

    List<RewardRule> getAll();

    RewardRule update(Long id, RewardRule rule);

    void delete(Long id);
}
