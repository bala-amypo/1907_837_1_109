package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_rules")
public class RewardRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;
    private String category;
    private boolean active;

    private Double multiplier;   // ✔ for reward calculation

    private String description;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Double getMultiplier() { return multiplier; }
    public void setMultiplier(Double multiplier) { this.multiplier = multiplier; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
