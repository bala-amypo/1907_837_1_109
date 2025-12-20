package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.entity.UserProfile;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserProfileService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public UserProfile register(@RequestBody RegisterRequest req) {
        UserProfile user = new UserProfile();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setState(req.getState());
        user.setCountry(req.getCountry());
        user.setMonthlyIncome(req.getMonthlyIncome());
        return userService.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest login) {
        UserProfile user = userService.getAllUsers()
                .stream()
                .filter(u -> u.getEmail().equals(login.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid login"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid login");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new JwtResponse(token);
    }
}
