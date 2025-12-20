package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-intents")
public class PurchaseIntentController {

    @Autowired
    private PurchaseIntentService intentService;

    @PostMapping
    public PurchaseIntentRecord create(@RequestBody PurchaseIntentRecord record) {
        return intentService.create(record);
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseIntentRecord> getByUser(@PathVariable Long userId) {
        return intentService.getByUserId(userId);
    }
}
