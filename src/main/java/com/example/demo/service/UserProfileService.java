package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import java.util.List;

public interface UserProfileService {

    UserProfile register(UserProfile user);

    UserProfile getUserById(Long id);

    List<UserProfile> getAllUsers();

    UserProfile updateUser(Long id, UserProfile updatedUser);

    void deleteUser(Long id);

    UserProfile findByEmail(String email);
}
