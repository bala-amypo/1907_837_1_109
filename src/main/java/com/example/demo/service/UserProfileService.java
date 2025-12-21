package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import java.util.List;

public interface UserProfileService {

    UserProfile register(UserProfile user);

    UserProfile getUserById(Long id);

    UserProfile getUserByEmail(String email);

    List<UserProfile> getAllUsers();

    UserProfile updateUser(Long id, UserProfile updated);

    void deleteUser(Long id);
}
