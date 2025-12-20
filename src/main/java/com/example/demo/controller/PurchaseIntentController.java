package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-intent")
public class PurchaseIntentController {

    private final PurchaseIntentService intentService;

    public PurchaseIntentController(PurchaseIntentService intentService) {
        this.intentService = intentService;
    }

    @PostMapping
    public PurchaseIntentRecord create(@RequestBody PurchaseIntentRecord intent) {
        return intentService.create(intent);
    }

    @GetMapping("/{userId}")
    public List<PurchaseIntentRecord> getByUserId(@PathVariable Long userId) {
        return intentService.getByUserId(userId);
    }
}
