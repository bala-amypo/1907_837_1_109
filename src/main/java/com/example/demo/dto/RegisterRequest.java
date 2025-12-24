// package com.example.demo.dto;

// public class RegisterRequest {

//     private String fullName;
//     private String email;
//     private String password;

//     private String phone;
//     private String city;
//     private String state;
//     private String country;

//     private Double monthlyIncome;

//     public RegisterRequest() {}

//     // GETTERS & SETTERS

//     public String getFullName() {
//         return fullName;
//     }

//     public void setFullName(String fullName) {
//         this.fullName = fullName;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public String getPhone() {
//         return phone;
//     }

//     public void setPhone(String phone) {
//         this.phone = phone;
//     }

//     public String getCity() {
//         return city;
//     }

//     public void setCity(String city) {
//         this.city = city;
//     }

//     public String getState() {
//         return state;
//     }

//     public void setState(String state) {
//         this.state = state;
//     }

//     public String getCountry() {
//         return country;
//     }

//     public void setCountry(String country) {
//         this.country = country;
//     }

//     public Double getMonthlyIncome() {
//         return monthlyIncome;
//     }

//     public void setMonthlyIncome(Double monthlyIncome) {
//         this.monthlyIncome = monthlyIncome;
//     }
// }

package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;

    public RegisterRequest() {}

    public RegisterRequest(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}