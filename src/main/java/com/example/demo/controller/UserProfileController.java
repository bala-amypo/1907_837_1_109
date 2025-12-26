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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userService;

    public UserProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
