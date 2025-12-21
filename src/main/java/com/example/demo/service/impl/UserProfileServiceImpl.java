package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userRepo;

    public UserProfileServiceImpl(UserProfileRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserProfile register(UserProfile user) {
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
        UserProfile existing = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setFullName(updatedUser.getFullName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());
        existing.setCity(updatedUser.getCity());
        existing.setState(updatedUser.getState());
        existing.setCountry(updatedUser.getCountry());
        existing.setMonthlyIncome(updatedUser.getMonthlyIncome());

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
                        new RuntimeException("User not found with email: " + email)
                );
    }
}
