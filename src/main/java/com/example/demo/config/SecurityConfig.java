// package com.example.demo.config;

// import com.example.demo.repository.UserProfileRepository;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     // 1. Define Password Encoder
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     // 2. Expose AuthenticationManager as a Bean (FIXES THE ERROR)
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     // 3. Define UserDetailsService to load user from Database
//     @Bean
//     public UserDetailsService userDetailsService(UserProfileRepository repository) {
//         return email -> repository.findByEmail(email)
//                 .map(u -> User.builder()
//                         .username(u.getEmail())
//                         .password(u.getPassword())
//                         .roles(u.getRole())
//                         .build())
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
//     }

//     // 4. Configure Security Filter Chain
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/auth/**", "/simple-status", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                 .anyRequest().authenticated()
//             );
        
//         return http.build();
//     }
// }

package com.example.demo.config;

import com.example.demo.repository.UserProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Define Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Expose AuthenticationManager as a Bean for AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 3. Define UserDetailsService to load user from Database
    @Bean
    public UserDetailsService userDetailsService(UserProfileRepository repository) {
        return email -> repository.findByEmail(email)
                .map(u -> User.builder()
                        .username(u.getEmail())
                        .password(u.getPassword())
                        // Note: roles() prepends "ROLE_" automatically. 
                        // authorities() is used if you want exact strings.
                        .authorities(u.getRole()) 
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    // 4. Configure Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
            .cors(Customizer.withDefaults()) // Enable CORS if needed
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions (JWT)
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/auth/**", "/simple-status", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Secure all other endpoints
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}