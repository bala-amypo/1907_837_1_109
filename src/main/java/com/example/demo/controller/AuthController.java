
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

// package com.example.demo.controller;

// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.JwtResponse;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.security.JwtUtil;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final UserProfileRepository repo;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authManager;
//     private final JwtUtil jwtUtil;

//     public AuthController(UserProfileRepository repo,
//                           PasswordEncoder passwordEncoder,
//                           AuthenticationManager authManager,
//                           JwtUtil jwtUtil) {

//         this.repo = repo;
//         this.passwordEncoder = passwordEncoder;
//         this.authManager = authManager;
//         this.jwtUtil = jwtUtil;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest req) {

//         UserProfile user = new UserProfile();
//         user.setFullName(req.getFullName());
//         user.setEmail(req.getEmail());
//         user.setPassword(passwordEncoder.encode(req.getPassword()));
//         user.setRole(req.getRole());
//         user.setUserId(req.getUserId());
//         repo.save(user);

//         String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

//         return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {

//         authManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         req.getEmail(), req.getPassword()
//                 )
//         );

//         UserProfile user = repo.findByEmail(req.getEmail())
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

//         return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getEmail(), user.getRole()));
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserProfileService userProfileService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userProfileService = userProfileService;
        this.jwtUtil = jwtUtil;
    }

    /* =======================================================
     *                     REGISTER
     * ======================================================= */
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {

        // Convert DTO -> Entity
        UserProfile user = new UserProfile();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setUserId(request.getUserId());

        // Save user
        UserProfile saved = userProfileService.createUser(user);

        // Generate token
        String token = jwtUtil.generateToken(
                saved.getId(),
                saved.getEmail(),
                saved.getRole()
        );

        // Return JWT Response
        JwtResponse response = new JwtResponse(
                token,
                saved.getId(),
                saved.getEmail(),
                saved.getRole()
        );

        return ResponseEntity.ok(response);
    }

    /* =======================================================
     *                     LOGIN
     * ======================================================= */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        // Authenticate username/password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Fetch user
        UserProfile user = userProfileService.findByEmail(request.getEmail());

        // Generate token
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        // Build response
        JwtResponse response = new JwtResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }
}
