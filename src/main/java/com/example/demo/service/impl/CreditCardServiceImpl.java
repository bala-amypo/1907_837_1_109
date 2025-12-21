package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRecordRepository repo;

    public CreditCardServiceImpl(CreditCardRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public CreditCardRecord create(CreditCardRecord card) {
        return repo.save(card);
    }

    @Override
    public CreditCardRecord getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCardRecord> getAll() {
        return repo.findAll();
    }

    @Override
    public CreditCardRecord update(Long id, CreditCardRecord updated) {
        CreditCardRecord c = getById(id);

        c.setCardName(updated.getCardName());
        c.setBank(updated.getBank());
        c.setCategory(updated.getCategory());
        c.setRewardPoints(updated.getRewardPoints());

        return repo.save(c);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
