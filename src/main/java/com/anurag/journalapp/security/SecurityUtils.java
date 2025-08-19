package com.anurag.journalapp.security;

import com.anurag.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUtils {
    public static ObjectId getCurrentUserId(Authentication authentication, UserRepository userRepository) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email))
                .getId();
    }
}

