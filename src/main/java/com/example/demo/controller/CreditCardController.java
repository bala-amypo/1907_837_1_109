package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService cardService;

    @PostMapping
    public CreditCardRecord create(@RequestBody CreditCardRecord card) {
        return cardService.create(card);
    }

    @GetMapping("/{id}")
    public CreditCardRecord get(@PathVariable Long id) {
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
    public String delete(@PathVariable Long id) {
        cardService.delete(id);
        return "Card deleted";
    }
}
