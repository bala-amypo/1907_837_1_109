

package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String fullName;
    //@one to many
    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private Boolean active;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = "USER";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;
// import java.util.Set;

// @Entity
// public class UserProfile {
//     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @Column(unique = true) private String userId;
//     private String fullName;
//     @Column(unique = true) private String email;
//     private String password;
//     private String role;
//     private Boolean active;
//     private LocalDateTime createdAt;

//     @ManyToMany
//     @JoinTable(name = "favourite_cards", 
//                joinColumns = @JoinColumn(name = "user_id"),
//                inverseJoinColumns = @JoinColumn(name = "card_id"))
//     private Set<CreditCardRecord> favouriteCards;

//     @PrePersist
//     protected void onCreate() { this.createdAt = LocalDateTime.now(); }

//     // Getters and Setters ...
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
// }