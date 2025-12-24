// package com.example.demo.service;

// import com.example.demo.entity.PurchaseIntentRecord;
// import java.util.List;

// public interface PurchaseIntentService {

//     PurchaseIntentRecord create(PurchaseIntentRecord intent);

//     List<PurchaseIntentRecord> getByUserId(Long userId);
// }
package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentService {
    PurchaseIntentRecord createIntent(PurchaseIntentRecord intent);
    List<PurchaseIntentRecord> getIntentsByUser(Long userId);
    PurchaseIntentRecord getIntentById(Long id);
    List<PurchaseIntentRecord> getAllIntents();
}