package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentService {

    PurchaseIntentRecord create(PurchaseIntentRecord intent);

    List<PurchaseIntentRecord> getByUserId(Long userId);
}
