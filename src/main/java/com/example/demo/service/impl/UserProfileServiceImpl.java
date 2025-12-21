package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(UserProfileRepository userRepo,
                                  PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfile register(UserProfile user) {

        // 🔥 ENCODE PASSWORD BEFORE SAVE
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserProfile updateUser(Long id, UserProfile updatedUser) {

        UserProfile existing = getUserById(id);

        existing.setFullName(updatedUser.getFullName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());
        existing.setCity(updatedUser.getCity());
        existing.setState(updatedUser.getState());
        existing.setCountry(updatedUser.getCountry());
        existing.setMonthlyIncome(updatedUser.getMonthlyIncome());

        // 🔥 If password changed → encode again
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found with email: " + email));
    }
}
