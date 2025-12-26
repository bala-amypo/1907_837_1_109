
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

// Spring Web Imports
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Spring Security Imports
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

// Project Specific Imports (Adjust package names if they differ in your project)
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserProfileService userService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // CONSTRUCTOR INJECTION
    public AuthController(UserProfileService userService,
                          UserProfileRepository userProfileRepository,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {
        UserProfile user = new UserProfile();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        
        // CRITICAL: Encode the password so login can verify it
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        
        user.setRole(req.getRole() != null ? req.getRole() : "USER");
        user.setUserId(req.getUserId());
        user.setActive(true);

        UserProfile savedUser = userService.createUser(user);
        
        // Generate token using the Saved User's details
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        return ResponseEntity.ok(new JwtResponse(token, savedUser.getId(), savedUser.getEmail(), savedUser.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        // 1. Authenticate the user (checks encoded password)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        
        // 2. Find user to get Role and ID for token
        UserProfile user = userProfileRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate the JWT
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
    }
}