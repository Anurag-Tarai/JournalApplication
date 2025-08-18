package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.request.RegisterRequest;
import com.anurag.journalapp.dto.response.RegisterResponse;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.enums.UserRole;
import com.anurag.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // User Register
    public ResponseEntity<?> register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already in use");
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

        RegisterResponse resp = new RegisterResponse(
                saved.getId() != null ? saved.getId().toHexString() : null,
                saved.getEmail(),
                saved.getFirstName(),
                saved.getLastName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // Register Admin
    public ResponseEntity<?> registerAdmin(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already in use");
        }

        String hash = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(email)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passwordHash(hash)
                .roles(EnumSet.of(UserRole.ADMIN))
                .enabled(true)
                .accountLocked(false)
                .build();

        User saved = userRepository.save(user);

        RegisterResponse resp = new RegisterResponse(
                saved.getId() != null ? saved.getId().toHexString() : null,
                saved.getEmail(),
                saved.getFirstName(),
                saved.getLastName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


    // Update Profile Name
    public ResponseEntity<?> updateProfileName(ObjectId userId, String firstName, String lastName) {
        return userRepository.findById(userId).map(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            userRepository.save(u);
            return ResponseEntity.ok().build(); // 200 OK with no body
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

  // Delete User By Email

    public boolean deleteByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).map(u -> {
            userRepository.delete(u); // relies on cascade for journals
            return true;
        }).orElse(false);
    }
}