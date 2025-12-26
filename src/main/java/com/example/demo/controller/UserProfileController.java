// package com.example.demo.controller;

// import com.example.demo.entity.UserProfile;
// import com.example.demo.service.UserProfileService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/users")
// public class UserProfileController {
    
//     private final UserProfileService userProfileService;

//     public UserProfileController(UserProfileService userProfileService) {
//         this.userProfileService = userProfileService;
//     }

//     @PostMapping
//     public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
//         return ResponseEntity.ok(userProfileService.createUser(user));
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
//         return ResponseEntity.ok(userProfileService.getUserById(id));
//     }

//     @GetMapping
//     public ResponseEntity<List<UserProfile>> getAllUsers() {
//         return ResponseEntity.ok(userProfileService.getAllUsers());
//     }

//     @PutMapping("/{id}/status")
//     public ResponseEntity<UserProfile> updateUserStatus(@PathVariable Long id, @RequestParam boolean active) {
//         return ResponseEntity.ok(userProfileService.updateUserStatus(id, active));
//     }

//     @GetMapping("/lookup/{userId}")
//     public ResponseEntity<UserProfile> findByUserId(@PathVariable String userId) {
//         return ResponseEntity.ok(userProfileService.findByUserId(userId));
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userService;
    private final PasswordEncoder passwordEncoder;

    public UserProfileController(UserProfileService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. GET ALL USERS
    // Endpoint: GET /api/users
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 2. GET USER BY ID
    // Endpoint: GET /api/users/{id}
    // Note: Use the 'id' (Long) returned in the Login Response, not the String 'userId'
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        UserProfile user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // 3. CREATE USER (Admin or Manual Creation)
    // Endpoint: POST /api/users
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
        // Ensure password is encrypted even if created via this endpoint
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return ResponseEntity.ok(userService.createUser(user));
    }
}