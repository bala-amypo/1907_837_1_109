package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
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
    public UserProfile create(@RequestBody UserProfile user) {
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public UserProfile getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserProfile> getAll() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserProfile update(@PathVariable Long id, @RequestBody UserProfile user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
