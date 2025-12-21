package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository repo;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseIntentRecord create(PurchaseIntentRecord intent) {
        return repo.save(intent);
    }

    @Override
    public List<PurchaseIntentRecord> getByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
