package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.request.RegisterRequest;
import com.anurag.journalapp.dto.response.UserResponse;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.enums.UserRole;
import com.anurag.journalapp.exception.EmailAlreadyExistsException;
import com.anurag.journalapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;


@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User Register
    public UserResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        String hash = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(email)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passwordHash(hash)
                .roles(EnumSet.of(UserRole.USER))
                .enabled(true)
                .accountLocked(false)
                .build();

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId() != null ? saved.getId().toHexString() : null,
                saved.getEmail(),
                saved.getFirstName(),
                saved.getLastName()
        );
    }
}