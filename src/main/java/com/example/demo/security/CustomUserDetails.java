package com.example.demo.security;

import com.example.demo.entity.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserProfile user;

    public CustomUserDetails(UserProfile user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // no roles for now
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // BCrypt password
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // login using email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
