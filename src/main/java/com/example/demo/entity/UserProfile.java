package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    private String phone;
    private String city;
    private String state;
    private String country;
    private Double monthlyIncome;

    // getters
    public Long getId() { return id; }

    public String getFullName() { return fullName; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getPhone() { return phone; }

    public String getCity() { return city; }

    public String getState() { return state; }

    public String getCountry() { return country; }

    public Double getMonthlyIncome() { return monthlyIncome; }

    // setters
    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setCity(String city) { this.city = city; }

    public void setState(String state) { this.state = state; }

    public void setCountry(String country) { this.country = country; }

    public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
}
