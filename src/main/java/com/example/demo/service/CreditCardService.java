package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import java.util.List;

public interface CreditCardService {
    CreditCardRecord addCard(CreditCardRecord card);
    CreditCardRecord updateCard(Long id, CreditCardRecord updated);
    List<CreditCardRecord> getCardsByUser(Long userId);
    CreditCardRecord getCardById(Long id);
    List<CreditCardRecord> getAllCards();
}