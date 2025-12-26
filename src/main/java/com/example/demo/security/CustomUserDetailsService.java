// package com.example.demo.security;

// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// import org.springframework.stereotype.Service;

// import java.util.ArrayList;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserProfileRepository repo;

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//         UserProfile user = repo.findByEmail(email)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

//         return new org.springframework.security.core.userdetails.User(
//                 user.getEmail(),
//                 user.getPassword(),
//                 new ArrayList<>()  // authorities empty
//         );
//     }
// }

// package com.example.demo.security;

// import com.example.demo.entity.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import java.util.Collections;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {
    
//     private final UserProfileRepository userProfileRepository;

//     public CustomUserDetailsService(UserProfileRepository userProfileRepository) {
//         this.userProfileRepository = userProfileRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         UserProfile user = userProfileRepository.findByEmail(email)
//             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
//         return User.builder()
//             .username(user.getEmail())
//             .password(user.getPassword())
//             .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
//             .accountExpired(false)
//             .accountLocked(false)
//             .credentialsExpired(false)
//             .disabled(!user.getActive())
//             .build();
//     }
// }
package com.example.demo.security;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userRepository;

    public CustomUserDetailsService(UserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}