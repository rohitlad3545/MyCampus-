package com.rohit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rohit.entites.User;
import com.rohit.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));

        if (existingUser.getRole() == null) {
            throw new UsernameNotFoundException(
                    "No role assigned to user: " + username);
        }

        String role = existingUser.getRole().name();

        // Match SecurityConfiguration's hasAuthority("ADMIN"), etc.
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                existingUser.getUsername(),
                existingUser.getPassword(),
                authorities);
    }
}