package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserProfileService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserProfileService userService,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    // ===================================================
    //  REGISTER USER
    // ===================================================
    @PostMapping("/register")
    public UserProfile register(@RequestBody RegisterRequest req) {

        UserProfile user = new UserProfile();

        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());   // Service will encode
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setState(req.getState());
        user.setCountry(req.getCountry());
        user.setMonthlyIncome(req.getMonthlyIncome()); // ensure Double

        return userService.register(user); // password encoded inside service
    }

    // ===================================================
    //  LOGIN → RETURN JWT TOKEN
    // ===================================================
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest req) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(), 
                            req.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(req.getEmail());
        return new JwtResponse(token);
    }
}
