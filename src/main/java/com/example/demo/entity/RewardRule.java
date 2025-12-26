

// package com.example.demo.entity;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "reward_rules")
// public class RewardRule {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Long cardId;
//     private String category;
//     private String rewardType;
//     private Double multiplier;
//     private Boolean active;

//     // Getters and Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public Long getCardId() { return cardId; }
//     public void setCardId(Long cardId) { this.cardId = cardId; }

//     public String getCategory() { return category; }
//     public void setCategory(String category) { this.category = category; }

//     public String getRewardType() { return rewardType; }
//     public void setRewardType(String rewardType) { this.rewardType = rewardType; }

//     public Double getMultiplier() { return multiplier; }
//     public void setMultiplier(Double multiplier) { this.multiplier = multiplier; }

//     public Boolean getActive() { return active; }
//     public void setActive(Boolean active) { this.active = active; }
// }

package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
public class RewardRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardId;
    private String category;
    private String rewardType;
    private Double multiplier;
    private Boolean active;

    // Getters and Setters ...
    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getMultiplier() { return multiplier; }
    public void setMultiplier(Double multiplier) { this.multiplier = multiplier; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}