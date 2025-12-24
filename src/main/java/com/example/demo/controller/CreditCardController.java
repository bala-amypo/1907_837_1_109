// package com.example.demo.controller;

// import com.example.demo.entity.CreditCardRecord;
// import com.example.demo.service.CreditCardService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/cards")
// public class CreditCardController {

//     private final CreditCardService service;

//     public CreditCardController(CreditCardService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public CreditCardRecord create(@RequestBody CreditCardRecord card) {
//         return service.addCard(card);
//     }

//     @PutMapping("/{id}")
//     public CreditCardRecord update(@PathVariable Long id, @RequestBody CreditCardRecord card) {
//         return service.updateCard(id, card);
//     }

//     @GetMapping("/user/{userId}")
//     public List<CreditCardRecord> getByUser(@PathVariable Long userId) {
//         return service.getCardsByUser(userId);
//     }

//     @GetMapping("/{id}")
//     public CreditCardRecord getById(@PathVariable Long id) {
//         return service.getCardById(id);
//     }

//     @GetMapping
//     public List<CreditCardRecord> getAll() {
//         return service.getAllCards();
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@SecurityRequirement(name = "JWT")
@Tag(name = "Credit Cards", description = "Credit card management endpoints")
public class CreditCardController {
    
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    @Operation(summary = "Add a new credit card")
    public ResponseEntity<CreditCardRecord> addCard(@Valid @RequestBody CreditCardRecord card) {
        return ResponseEntity.ok(creditCardService.addCard(card));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update credit card")
    public ResponseEntity<CreditCardRecord> updateCard(@PathVariable Long id, @Valid @RequestBody CreditCardRecord card) {
        return ResponseEntity.ok(creditCardService.updateCard(id, card));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get cards by user")
    public ResponseEntity<List<CreditCardRecord>> getCardsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(creditCardService.getCardsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get card by ID")
    public ResponseEntity<CreditCardRecord> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(creditCardService.getCardById(id));
    }

    @GetMapping
    @Operation(summary = "Get all cards")
    public ResponseEntity<List<CreditCardRecord>> getAllCards() {
        return ResponseEntity.ok(creditCardService.getAllCards());
    }
}