package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id)
                );
    }

    @Override
    public UserProfile getUserByEmail(String email) {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email)
                );
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUser(Long id, UserProfile updatedProfile) {

        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id)
                );

        existing.setName(updatedProfile.getName());
        existing.setEmail(updatedProfile.getEmail());
        existing.setAge(updatedProfile.getAge());
        existing.setGender(updatedProfile.getGender());
        existing.setCity(updatedProfile.getCity());
        existing.setState(updatedProfile.getState());
        existing.setCountry(updatedProfile.getCountry());
        existing.setPhone(updatedProfile.getPhone());
        existing.setMonthlyIncome(updatedProfile.getMonthlyIncome());

        return userProfileRepository.save(existing);
    }
}
