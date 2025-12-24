// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;
// import java.util.Set;

// @Entity
// @Table(name = "user_profiles")
// public class UserProfile {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false)
//     private String userId;

//     @Column(nullable = false)
//     private String fullName;

//     @Column(unique = true, nullable = false)
//     private String email;

//     @Column(nullable = false)
//     private String password;

//     @Column(nullable = false)
//     private String role = "USER";

//     @Column(nullable = false)
//     private Boolean active = true;

//     @Column(nullable = false)
//     private LocalDateTime createdAt;

//     @ManyToMany
//     @JoinTable(
//         name = "user_favourite_cards",
//         joinColumns = @JoinColumn(name = "user_id"),
//         inverseJoinColumns = @JoinColumn(name = "card_id")
//     )
//     private Set<CreditCardRecord> favouriteCards;

//     @PrePersist
//     protected void onCreate() {
//         createdAt = LocalDateTime.now();
//     }

//     public void prePersist() {
//         onCreate();
//     }

//     public UserProfile() {}

//     public UserProfile(String userId, String fullName, String email, String password, String role) {
//         this.userId = userId;
//         this.fullName = fullName;
//         this.email = email;
//         this.password = password;
//         this.role = role;
//     }

//     // Getters and Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getUserId() { return userId; }
//     public void setUserId(String userId) { this.userId = userId; }

//     public String getFullName() { return fullName; }
//     public void setFullName(String fullName) { this.fullName = fullName; }

//     public String getEmail() { return email; }
//     public void setEmail(String email) { this.email = email; }

//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }

//     public String getRole() { return role; }
//     public void setRole(String role) { this.role = role; }

//     public Boolean getActive() { return active; }
//     public void setActive(Boolean active) { this.active = active; }

//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

//     public Set<CreditCardRecord> getFavouriteCards() { return favouriteCards; }
//     public void setFavouriteCards(Set<CreditCardRecord> favouriteCards) { this.favouriteCards = favouriteCards; }
// }


package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "USER";
    }
}