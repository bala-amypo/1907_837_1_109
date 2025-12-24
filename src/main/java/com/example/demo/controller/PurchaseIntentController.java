// package com.example.demo.controller;

// import com.example.demo.entity.PurchaseIntentRecord;
// import com.example.demo.service.PurchaseIntentService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/purchase-intent")
// public class PurchaseIntentController {

//     private final PurchaseIntentService intentService;

//     public PurchaseIntentController(PurchaseIntentService intentService) {
//         this.intentService = intentService;
//     }

//     @PostMapping
//     public PurchaseIntentRecord create(@RequestBody PurchaseIntentRecord intent) {
//         return intentService.create(intent);
//     }

//     @GetMapping("/{userId}")
//     public List<PurchaseIntentRecord> getByUserId(@PathVariable Long userId) {
//         return intentService.getByUserId(userId);
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intents")
@SecurityRequirement(name = "JWT")
@Tag(name = "Purchase Intents", description = "Purchase intent management endpoints")
public class PurchaseIntentController {
    
    private final PurchaseIntentService purchaseIntentService;

    public PurchaseIntentController(PurchaseIntentService purchaseIntentService) {
        this.purchaseIntentService = purchaseIntentService;
    }

    @PostMapping
    @Operation(summary = "Create a new purchase intent")
    public ResponseEntity<PurchaseIntentRecord> createIntent(@Valid @RequestBody PurchaseIntentRecord intent) {
        return ResponseEntity.ok(purchaseIntentService.createIntent(intent));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get intents by user")
    public ResponseEntity<List<PurchaseIntentRecord>> getIntentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(purchaseIntentService.getIntentsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get intent by ID")
    public ResponseEntity<PurchaseIntentRecord> getIntentById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseIntentService.getIntentById(id));
    }

    @GetMapping
    @Operation(summary = "Get all intents")
    public ResponseEntity<List<PurchaseIntentRecord>> getAllIntents() {
        return ResponseEntity.ok(purchaseIntentService.getAllIntents());
    }
}