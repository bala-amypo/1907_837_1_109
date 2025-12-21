package com.example.demo.repository;

import com.example.demo.entity.CreditCardRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRecordRepository extends JpaRepository<CreditCardRecord, Long> {

    @Query("SELECT c FROM CreditCardRecord c WHERE c.userId = :userId")
    List<CreditCardRecord> findActiveCardsByUser(Long userId);

    List<CreditCardRecord> findByUserId(Long userId);
}
