// package com.example.demo.controller;

// import com.example.demo.dto.JwtResponse;
// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.UserProfile;
// import com.example.demo.security.JwtUtil;
// import com.example.demo.service.UserProfileService;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     private final UserProfileService userService;
//     private final AuthenticationManager authManager;
//     private final JwtUtil jwtUtil;

//     public AuthController(UserProfileService userService,
//                           AuthenticationManager authManager,
//                           JwtUtil jwtUtil) {
//         this.userService = userService;
//         this.authManager = authManager;
//         this.jwtUtil = jwtUtil;
//     }

//     // ===================================================
//     //  REGISTER USER
//     // ===================================================
//     @PostMapping("/register")
//     public UserProfile register(@RequestBody RegisterRequest req) {

//         UserProfile user = new UserProfile();

//         user.setFullName(req.getFullName());
//         user.setEmail(req.getEmail());
//         user.setPassword(req.getPassword());   // Service will encode
//         user.setPhone(req.getPhone());
//         user.setCity(req.getCity());
//         user.setState(req.getState());
//         user.setCountry(req.getCountry());
//         user.setMonthlyIncome(req.getMonthlyIncome()); // ensure Double

//         return userService.register(user); // password encoded inside service
//     }

//     // ===================================================
//     //  LOGIN → RETURN JWT TOKEN
//     // ===================================================
//     @PostMapping("/login")
//     public JwtResponse login(@RequestBody LoginRequest req) {

//         try {
//             authManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(
//                             req.getEmail(), 
//                             req.getPassword()
//                     )
//             );
//         } catch (BadCredentialsException ex) {
//             throw new BadCredentialsException("Invalid email or password");
//         }

//         String token = jwtUtil.generateToken(req.getEmail());
//         return new JwtResponse(token);
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    
    private final UserProfileService userProfileService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserProfileService userProfileService, 
                         UserProfileRepository userProfileRepository,
                         AuthenticationManager authenticationManager, 
                         JwtUtil jwtUtil) {
        this.userProfileService = userProfileService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserProfile user = new UserProfile();
        user.setUserId(UUID.randomUUID().toString());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        
        UserProfile savedUser = userProfileService.createUser(user);
        
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
        
        return ResponseEntity.ok(new JwtResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserProfile user = userProfileRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        
        return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}