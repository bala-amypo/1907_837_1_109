// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "purchase_intents")
// public class PurchaseIntentRecord {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private Long userId;

//     @Column(nullable = false)
//     private Double amount;

//     @Column(nullable = false)
//     private String category;

//     @Column(nullable = false)
//     private String merchant;

//     @Column(nullable = false)
//     private LocalDateTime intentDate;

//     @PrePersist
//     protected void onCreate() {
//         intentDate = LocalDateTime.now();
//     }

//     public PurchaseIntentRecord() {}

//     public PurchaseIntentRecord(Long userId, Double amount, String category, String merchant) {
//         this.userId = userId;
//         this.amount = amount;
//         this.category = category;
//         this.merchant = merchant;
//     }

//     // Getters and Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public Long getUserId() { return userId; }
//     public void setUserId(Long userId) { this.userId = userId; }

//     public Double getAmount() { return amount; }
//     public void setAmount(Double amount) { this.amount = amount; }

//     public String getCategory() { return category; }
//     public void setCategory(String category) { this.category = category; }

//     public String getMerchant() { return merchant; }
//     public void setMerchant(String merchant) { this.merchant = merchant; }

//     public LocalDateTime getIntentDate() { return intentDate; }
//     public void setIntentDate(LocalDateTime intentDate) { this.intentDate = intentDate; }
// }

package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_intents")
public class PurchaseIntentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Double amount;
    private String category;
    private String merchant;
    private LocalDateTime intentDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant; }

    public LocalDateTime getIntentDate() { return intentDate; }
    public void setIntentDate(LocalDateTime intentDate) { this.intentDate = intentDate; }
}