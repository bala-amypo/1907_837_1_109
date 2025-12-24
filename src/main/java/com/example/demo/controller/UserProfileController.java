// package com.example.demo.controller;

// import com.example.demo.entity.UserProfile;
// import com.example.demo.service.UserProfileService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/users")
// public class UserProfileController {

//     private final UserProfileService service;

//     public UserProfileController(UserProfileService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public UserProfile createUser(@RequestBody UserProfile user) {
//         return service.register(user);
//     }

//     @GetMapping
//     public List<UserProfile> getAllUsers() {
//         return service.getAllUsers();
//     }

//     @GetMapping("/{id}")
//     public UserProfile getUser(@PathVariable Long id) {
//         return service.getUserById(id);
//     }

//     @PutMapping("/{id}")
//     public UserProfile updateUser(@PathVariable Long id, @RequestBody UserProfile user) {
//         return service.updateUser(id, user);
//     }

//     @DeleteMapping("/{id}")
//     public String deleteUser(@PathVariable Long id) {
//         service.deleteUser(id);
//         return "User deleted successfully";
//     }
// }

package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "JWT")
@Tag(name = "Users", description = "User management endpoints")
public class UserProfileController {
    
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserProfile> createUser(@Valid @RequestBody UserProfile user) {
        return ResponseEntity.ok(userProfileService.createUser(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userProfileService.getAllUsers());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update user status")
    public ResponseEntity<UserProfile> updateUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(userProfileService.updateUserStatus(id, active));
    }

    @GetMapping("/lookup/{userId}")
    @Operation(summary = "Lookup user by userId")
    public ResponseEntity<UserProfile> findByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userProfileService.findByUserId(userId));
    }
}