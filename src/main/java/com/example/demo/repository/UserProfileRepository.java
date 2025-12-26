
// package com.example.demo.repository;
// import com.example.demo.entity.UserProfile;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.Optional;
// public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
//     boolean existsByEmail(String email);
//     boolean existsByUserId(String userId);
//     Optional<UserProfile> findByEmail(String email);
// }

package com.example.demo.repository;

import com.example.demo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByUserId(String userId);
}