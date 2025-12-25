// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "credit_cards")
// public class CreditCardRecord {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private Long userId;

//     @Column(nullable = false)
//     private String cardName;

//     @Column(nullable = false)
//     private String issuer;

//     @Column(nullable = false)
//     private String cardType;

//     @Column(nullable = false)
//     private Double annualFee;

//     @Column(nullable = false)
//     private String status = "ACTIVE";

//     @Column(nullable = false)
//     private LocalDateTime createdAt;

//     @PrePersist
//     protected void onCreate() {
//         createdAt = LocalDateTime.now();
//     }

//     public void prePersist() {
//         onCreate();
//     }

//     public CreditCardRecord() {}

//     public CreditCardRecord(Long userId, String cardName, String issuer, String cardType, Double annualFee) {
//         this.userId = userId;
//         this.cardName = cardName;
//         this.issuer = issuer;
//         this.cardType = cardType;
//         this.annualFee = annualFee;
//     }

//     // Getters and Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public Long getUserId() { return userId; }
//     public void setUserId(Long userId) { this.userId = userId; }

//     public String getCardName() { return cardName; }
//     public void setCardName(String cardName) { this.cardName = cardName; }

//     public String getIssuer() { return issuer; }
//     public void setIssuer(String issuer) { this.issuer = issuer; }

//     public String getCardType() { return cardType; }
//     public void setCardType(String cardType) { this.cardType = cardType; }

//     public Double getAnnualFee() { return annualFee; }
//     public void setAnnualFee(Double annualFee) { this.annualFee = annualFee; }

//     public String getStatus() { return status; }
//     public void setStatus(String status) { this.status = status; }

//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
// }

package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_cards")
public class CreditCardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String cardName;
    private String issuer;
    private String cardType;
    private Double annualFee;
    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public Double getAnnualFee() { return annualFee; }
    public void setAnnualFee(Double annualFee) { this.annualFee = annualFee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}