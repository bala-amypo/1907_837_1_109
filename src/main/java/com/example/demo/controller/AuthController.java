package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.service.UserProfileService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserProfileService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UserProfileService userService,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public UserProfile register(@RequestBody RegisterRequest req) {

        UserProfile user = new UserProfile();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setState(req.getState());
        user.setCountry(req.getCountry());
        user.setMonthlyIncome(req.getMonthlyIncome());

        return userService.register(user);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest req) {

        // Step 1 — Authenticate credentials
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        // Step 2 — Load UserDetails
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(req.getEmail());

        // Step 3 — Generate token
        String token = jwtUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
