
// package com.example.demo.controller;

// import com.example.demo.dto.JwtResponse;
// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.security.JwtUtil;
// import com.example.demo.service.UserProfileService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final UserProfileService userService;
//     private final UserProfileRepository userProfileRepository;
//     private final AuthenticationManager authenticationManager;
//     private final JwtUtil jwtUtil;

//     // Manual constructor for setup alignment
//     public AuthController(UserProfileService userService, 
//                           UserProfileRepository userProfileRepository, 
//                           AuthenticationManager authenticationManager, 
//                           JwtUtil jwtUtil) {
//         this.userService = userService;
//         this.userProfileRepository = userProfileRepository;
//         this.authenticationManager = authenticationManager;
//         this.jwtUtil = jwtUtil;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
//         UserProfile user = new UserProfile();
//         user.setFullName(req.getFullName());
//         user.setEmail(req.getEmail());
//         user.setPassword(req.getPassword());
//         user.setRole(req.getRole() != null ? req.getRole() : "USER");
//         user.setUserId(req.getUserId() != null ? req.getUserId() : java.util.UUID.randomUUID().toString());
//         user.setActive(true);

//         UserProfile saved = userService.createUser(user);
//         String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        
//         return ResponseEntity.ok(new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole()));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
//         // Authenticate using the Manager defined in SecurityConfig
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
//         );

//         UserProfile user = userProfileRepository.findByEmail(req.getEmail()).orElseThrow();
//         String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

//         return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserProfileService userService;
    private final UserProfileRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(UserProfileService userService, UserProfileRepository userRepo, 
                          AuthenticationManager authManager, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
        UserProfile user = new UserProfile();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword())); // ENCODE
        user.setRole(req.getRole() != null ? req.getRole() : "USER");
        user.setUserId(req.getUserId());
        user.setActive(true);

        UserProfile saved = userService.createUser(user);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return ResponseEntity.ok(new JwtResponse(token, saved.getId(), saved.getEmail(), saved.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserProfile user = userRepo.findByEmail(req.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}