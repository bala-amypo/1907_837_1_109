package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository repo;

    @Override
    public UserProfile register(UserProfile user) {
        return repo.save(user);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserProfile updateUser(Long id, UserProfile updated) {

        UserProfile existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        // These fields match your actual UserProfile entity — NOT getName()
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setCountry(updated.getCountry());
        existing.setMonthlyIncome(updated.getMonthlyIncome());

        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public UserProfile findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));
    }
}
