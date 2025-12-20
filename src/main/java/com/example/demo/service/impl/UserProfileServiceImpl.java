package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repo;

    public UserProfileServiceImpl(UserProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserProfile register(UserProfile user) {
        return repo.save(user);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public UserProfile updateUser(Long id, UserProfile updated) {
        UserProfile user = getUserById(id);

        user.setFullName(updated.getFullName());
        user.setPhone(updated.getPhone());
        user.setCity(updated.getCity());
        user.setState(updated.getState());
        user.setCountry(updated.getCountry());
        user.setMonthlyIncome(updated.getMonthlyIncome());

        return repo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    @Override
    public UserProfile findByEmail(String email) {
        return repo.findByEmail(email);
    }
}
