package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    private final CreditCardService cardService;

    public CreditCardController(CreditCardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public CreditCardRecord create(@RequestBody CreditCardRecord card) {
        return cardService.create(card);
    }

    @GetMapping("/{id}")
    public CreditCardRecord getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @GetMapping
    public List<CreditCardRecord> getAll() {
        return cardService.getAll();
    }

    @PutMapping("/{id}")
    public CreditCardRecord update(@PathVariable Long id, @RequestBody CreditCardRecord card) {
        return cardService.update(id, card);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }
}
