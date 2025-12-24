// package com.example.demo.service;

// import com.example.demo.entity.UserProfile;
// import java.util.List;

// public interface UserProfileService {
//     UserProfile createUser(UserProfile profile);
//     UserProfile getUserById(Long id);
//     UserProfile findByUserId(String userId);
//     List<UserProfile> getAllUsers();
//     UserProfile updateUserStatus(Long id, boolean active);
// }

package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    // Manual constructor for Dependency Injection (required for the Test Class setup)
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        // Business Rule: Email and UserId must be unique
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new BadRequestException("Email already exists: " + profile.getEmail());
        }
        if (userProfileRepository.existsByUserId(profile.getUserId())) {
            throw new BadRequestException("User ID already exists: " + profile.getUserId());
        }

        // Encrypt the password before saving
        String rawPassword = profile.getPassword();
        if (rawPassword != null) {
            profile.setPassword(passwordEncoder.encode(rawPassword));
        }

        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> findByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }
}