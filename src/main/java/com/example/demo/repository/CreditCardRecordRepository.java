package com.example.demo.repository;

import com.example.demo.entity.CreditCardRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRecordRepository extends JpaRepository<CreditCardRecord, Long> {
    List<CreditCardRecord> findByUserId(Long userId);
    
    @Query("SELECT c FROM CreditCardRecord c WHERE c.userId = :userId AND c.status = 'ACTIVE'")
    List<CreditCardRecord> findActiveCardsByUser(@Param("userId") Long userId);
}