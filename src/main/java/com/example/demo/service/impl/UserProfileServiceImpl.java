// // package com.example.demo.service.impl;

// // import com.example.demo.entity.UserProfile;
// // import com.example.demo.exception.BadRequestException;
// // import com.example.demo.exception.ResourceNotFoundException;
// // import com.example.demo.repository.UserProfileRepository;
// // import com.example.demo.service.UserProfileService;
// // import org.springframework.stereotype.Service;

// // import java.util.List;
// // import java.util.UUID;

// // @Service
// // public class UserProfileServiceImpl implements UserProfileService {
    
// //     private final UserProfileRepository userProfileRepository;
// //     private final PasswordEncoder passwordEncoder;

// //     public UserProfileServiceImpl(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
// //         this.userProfileRepository = userProfileRepository;
// //         this.passwordEncoder = passwordEncoder;
// //     }

// //     @Override
// //     public UserProfile createUser(UserProfile profile) {
// //         if (userProfileRepository.existsByEmail(profile.getEmail())) {
// //             throw new BadRequestException("Email already exists");
// //         }
        
// //         if (profile.getUserId() == null) {
// //             profile.setUserId(UUID.randomUUID().toString());
// //         }
        
// //         if (userProfileRepository.existsByUserId(profile.getUserId())) {
// //             throw new BadRequestException("User ID already exists");
// //         }
        
// //         profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        
// //         if (profile.getRole() == null) {
// //             profile.setRole("USER");
// //         }
        
// //         return userProfileRepository.save(profile);
// //     }

// //     @Override
// //     public UserProfile getUserById(Long id) {
// //         return userProfileRepository.findById(id)
// //             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
// //     }

// //     @Override
// //     public UserProfile findByUserId(String userId) {
// //         return userProfileRepository.findAll().stream()
// //             .filter(user -> userId.equals(user.getUserId()))
// //             .findFirst()
// //             .orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
// //     }

// //     @Override
// //     public List<UserProfile> getAllUsers() {
// //         return userProfileRepository.findAll();
// //     }

// //     @Override
// //     public UserProfile updateUserStatus(Long id, boolean active) {
// //         UserProfile user = getUserById(id);
// //         user.setActive(active);
// //         return userProfileRepository.save(user);
// //     }
    
// //     public interface PasswordEncoder {
// //         String encode(CharSequence rawPassword);
// //         boolean matches(CharSequence rawPassword, String encodedPassword);
// //     }
// // }

// package com.example.demo.service.impl;

// import com.example.demo.entity.UserProfile;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.UserProfileRepository;
// import com.example.demo.service.UserProfileService;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.UUID;

// @Service
// public class UserProfileServiceImpl implements UserProfileService {

//     private final UserProfileRepository userProfileRepository;
//     private final PasswordEncoder passwordEncoder;

//     public UserProfileServiceImpl(UserProfileRepository userProfileRepository,
//                                   PasswordEncoder passwordEncoder) {
//         this.userProfileRepository = userProfileRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public UserProfile createUser(UserProfile profile) {

//         if (userProfileRepository.existsByEmail(profile.getEmail())) {
//             throw new BadRequestException("Email already exists");
//         }

//         if (profile.getUserId() == null) {
//             profile.setUserId(UUID.randomUUID().toString());
//         }

//         if (userProfileRepository.existsByUserId(profile.getUserId())) {
//             throw new BadRequestException("User ID already exists");
//         }

//         profile.setPassword(passwordEncoder.encode(profile.getPassword()));

//         if (profile.getRole() == null) {
//             profile.setRole("USER");
//         }

//         return userProfileRepository.save(profile);
//     }

//     @Override
//     public UserProfile getUserById(Long id) {
//         return userProfileRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
//     }

//     @Override
//     public UserProfile findByUserId(String userId) {
//         return userProfileRepository.findAll().stream()
//                 .filter(user -> userId.equals(user.getUserId()))
//                 .findFirst()
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
//     }

//     @Override
//     public List<UserProfile> getAllUsers() {
//         return userProfileRepository.findAll();
//     }

//     @Override
//     public UserProfile updateUserStatus(Long id, boolean active) {
//         UserProfile user = getUserById(id);
//         user.setActive(active);
//         return userProfileRepository.save(user);
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfile createUser(UserProfile profile) {
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (userProfileRepository.existsByUserId(profile.getUserId())) {
            throw new BadRequestException("User ID already exists");
        }
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id).orElseThrow();
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }
}