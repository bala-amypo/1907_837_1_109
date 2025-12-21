package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile user) {
        return service.register(user);
    }

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserProfile getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserProfile updateUser(@PathVariable Long id, @RequestBody UserProfile user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }
}
