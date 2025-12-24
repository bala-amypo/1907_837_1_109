// package com.example.demo.controller;

// import com.example.demo.dto.JwtResponse;
// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.security.JwtUtil;
// import com.example.demo.service.UserProfileService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.UUID;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {
    
//     private final UserProfileService userProfileService;
//     private final UserProfileRepository userProfileRepository;
//     private final JwtUtil jwtUtil;

//     public AuthController(UserProfileService userProfileService, 
//                          UserProfileRepository userProfileRepository,
//                          JwtUtil jwtUtil) {
//         this.userProfileService = userProfileService;
//         this.userProfileRepository = userProfileRepository;
//         this.jwtUtil = jwtUtil;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
//         UserProfile user = new UserProfile();
//         user.setUserId(UUID.randomUUID().toString());
//         user.setFullName(request.getFullName());
//         user.setEmail(request.getEmail());
//         user.setPassword(request.getPassword());
//         user.setRole(request.getRole() != null ? request.getRole() : "USER");
        
//         UserProfile savedUser = userProfileService.createUser(user);
        
//         String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
        
//         return ResponseEntity.ok(new JwtResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole()));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
//         UserProfile user = userProfileRepository.findByEmail(request.getEmail())
//             .orElseThrow(() -> new RuntimeException("User not found"));
        
//         String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        
//         return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
//     }
// }
// package com.example.demo.controller;

// import com.example.demo.dto.*;
// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.security.JwtUtil;
// import com.example.demo.service.UserProfileService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// @RequiredArgsConstructor
// public class AuthController {
//     private final UserProfileService userService;
//     private final UserProfileRepository userRepository;
//     private final AuthenticationManager authenticationManager;
//     private final JwtUtil jwtUtil;

//     @PostMapping("/register")
//     public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
//         UserProfile user = new UserProfile();
//         user.setUserId(req.getUserId() != null ? req.getUserId() : java.util.UUID.randomUUID().toString());
//         user.setEmail(req.getEmail());
//         user.setFullName(req.getFullName());
//         user.setPassword(req.getPassword());
//         user.setRole(req.getRole());
//         user.setActive(true);
        
//         UserProfile saved = userService.createUser(user);
//         String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
//         return ResponseEntity.ok(new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole()));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
//         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
//         UserProfile user = userRepository.findByEmail(req.getEmail()).orElseThrow();
//         String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
//         return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserProfileService userService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // Manual constructor for setup alignment
    public AuthController(UserProfileService userService, 
                          UserProfileRepository userProfileRepository, 
                          AuthenticationManager authenticationManager, 
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
        UserProfile user = new UserProfile();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole() != null ? req.getRole() : "USER");
        user.setUserId(req.getUserId() != null ? req.getUserId() : java.util.UUID.randomUUID().toString());
        user.setActive(true);

        UserProfile saved = userService.createUser(user);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        
        return ResponseEntity.ok(new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        // Authenticate using the Manager defined in SecurityConfig
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserProfile user = userProfileRepository.findByEmail(req.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}