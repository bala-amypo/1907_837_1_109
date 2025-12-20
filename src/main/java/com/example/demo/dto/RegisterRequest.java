package com.example.demo.dto;

public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;

    private String phone;
    private String city;
    private String state;
    private String country;
    private Double monthlyIncome;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }
}
