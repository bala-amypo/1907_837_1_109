package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import java.util.List;

public interface CreditCardService {

    CreditCardRecord create(CreditCardRecord card);

    CreditCardRecord getById(Long id);

    List<CreditCardRecord> getAll();

    CreditCardRecord update(Long id, CreditCardRecord card);

    void delete(Long id);
}
