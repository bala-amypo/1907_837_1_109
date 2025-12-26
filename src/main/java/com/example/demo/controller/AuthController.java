
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

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserProfileService userService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * TECHNICAL CONSTRAINT: Exact Constructor Order Required for Testing
     * 1. UserProfileService
     * 2. UserProfileRepository
     * 3. AuthenticationManager
     * 4. JwtUtil
     */
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
        
        // Use external userId if provided, otherwise generate one
        user.setUserId(req.getUserId() != null ? req.getUserId() : java.util.UUID.randomUUID().toString());
        user.setActive(true);

        // Service handles duplicate check and password encoding
        UserProfile savedUser = userService.createUser(user);

        // Generate Token
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());

        return ResponseEntity.ok(new JwtResponse(
                token, 
                savedUser.getId(), 
                savedUser.getEmail(), 
                savedUser.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        try {
            // 1. Authenticate via Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            // 2. Fetch User from DB
            UserProfile user = userProfileRepository.findByEmail(req.getEmail())
                    .orElseThrow(() -> new BadRequestException("User not found"));

            // 3. TECHNICAL CONSTRAINT: Only active users are allowed to log in
            if (user.getActive() == null || !user.getActive()) {
                throw new BadRequestException("User account is inactive");
            }

            // 4. Generate JWT
            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

            return ResponseEntity.ok(new JwtResponse(
                    token, 
                    user.getId(), 
                    user.getEmail(), 
                    user.getRole()
            ));

        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid email or password");
        }
    }
}