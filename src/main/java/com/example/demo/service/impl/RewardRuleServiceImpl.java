// package com.example.demo.service.impl;

// import com.example.demo.entity.RewardRule;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.RewardRuleRepository;
// import com.example.demo.service.RewardRuleService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class RewardRuleServiceImpl implements RewardRuleService {
    
//     private final RewardRuleRepository rewardRuleRepository;

//     public RewardRuleServiceImpl(RewardRuleRepository rewardRuleRepository) {
//         this.rewardRuleRepository = rewardRuleRepository;
//     }

//     @Override
//     public RewardRule createRule(RewardRule rule) {
//         if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
//             throw new BadRequestException("Price multiplier must be > 0");
//         }
//         return rewardRuleRepository.save(rule);
//     }

//     @Override
//     public RewardRule updateRule(Long id, RewardRule updated) {
//         RewardRule existing = rewardRuleRepository.findById(id)
//             .orElseThrow(() -> new ResourceNotFoundException("Reward rule not found with id: " + id));
        
//         if (updated.getMultiplier() == null || updated.getMultiplier() <= 0) {
//             throw new BadRequestException("Price multiplier must be > 0");
//         }
        
//         existing.setCategory(updated.getCategory());
//         existing.setRewardType(updated.getRewardType());
//         existing.setMultiplier(updated.getMultiplier());
//         existing.setActive(updated.getActive());
        
//         return rewardRuleRepository.save(existing);
//     }

//     @Override
//     public List<RewardRule> getRulesByCard(Long cardId) {
//         return rewardRuleRepository.findAll().stream()
//             .filter(rule -> cardId.equals(rule.getCardId()))
//             .toList();
//     }

//     @Override
//     public List<RewardRule> getActiveRules() {
//         return rewardRuleRepository.findByActiveTrue();
//     }

//     @Override
//     public List<RewardRule> getAllRules() {
//         return rewardRuleRepository.findAll();
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardRuleServiceImpl implements RewardRuleService {
    private final RewardRuleRepository rewardRuleRepository;

    @Override
    public RewardRule createRule(RewardRule rule) {
        // Business Rule: multiplier must be positive
        if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return rewardRuleRepository.save(rule);
    }

    @Override
    public List<RewardRule> getActiveRules() {
        return rewardRuleRepository.findByActiveTrue();
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRuleRepository.findAll();
    }
    
    // Additional methods (updateRule, getRulesByCard) should be implemented here
}