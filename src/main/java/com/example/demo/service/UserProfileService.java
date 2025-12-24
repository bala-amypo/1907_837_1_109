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

package com.example.demo.service;
import com.example.demo.entity.UserProfile;
import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    UserProfile createUser(UserProfile profile);
    UserProfile getUserById(Long id);
    List<UserProfile> getAllUsers();
    Optional<UserProfile> findByEmail(String email);
}