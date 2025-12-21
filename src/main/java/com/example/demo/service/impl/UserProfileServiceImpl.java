package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserProfile register(UserProfile user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserProfile getUserByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserProfile updateUser(Long id, UserProfile updated) {
        UserProfile existing = getUserById(id);

        existing.setFullName(updated.getFullName());
        existing.setPhone(updated.getPhone());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setCountry(updated.getCountry());
        existing.setMonthlyIncome(updated.getMonthlyIncome());

        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        UserProfile user = getUserById(id);
        repo.delete(user);
    }
}
