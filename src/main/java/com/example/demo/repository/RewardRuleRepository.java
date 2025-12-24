package com.example.demo.repository;

import com.example.demo.entity.RewardRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RewardRuleRepository extends JpaRepository<RewardRule, Long> {
    @Query("SELECT r FROM RewardRule r WHERE r.cardId = :cardId AND r.category = :category AND r.active = true")
    List<RewardRule> findActiveRulesForCardCategory(@Param("cardId") Long cardId, @Param("category") String category);
    List<RewardRule> findByActiveTrue();
}