package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class CreditCardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bank;
    private String cardName;
    private String category;
    private Integer rewardPoints;

    // getters
    public Long getId() { return id; }

    public String getBank() { return bank; }

    public String getCardName() { return cardName; }

    public String getCategory() { return category; }

    public Integer getRewardPoints() { return rewardPoints; }

    // setters
    public void setBank(String bank) { this.bank = bank; }

    public void setCardName(String cardName) { this.cardName = cardName; }

    public void setCategory(String category) { this.category = category; }

    public void setRewardPoints(Integer rewardPoints) { this.rewardPoints = rewardPoints; }
}
