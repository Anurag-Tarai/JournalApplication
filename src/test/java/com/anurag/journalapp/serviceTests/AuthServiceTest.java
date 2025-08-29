package com.anurag.journalapp.serviceTests;

import com.anurag.journalapp.dto.request.RegisterRequest;
import com.anurag.journalapp.dto.response.UserResponse;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.enums.UserRole;
import com.anurag.journalapp.exception.EmailAlreadyExistsException;
import com.anurag.journalapp.repository.UserRepository;
import com.anurag.journalapp.service.AuthService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;


    // Manual style, Works even without MockitoExtension.
//    @BeforeEach
//    void setUp() {
//        userRepository = mock(UserRepository.class);
//        passwordEncoder = mock(PasswordEncoder.class);
//        authService = new AuthService(userRepository, passwordEncoder);
//    }


    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        given(userRepository.existsByEmail("test@example.com")).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("hashedPassword");

        User savedUser = User.builder()
                .id(new ObjectId())
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .passwordHash("hashedPassword")
                .roles(EnumSet.of(UserRole.USER))
                .enabled(true)
                .accountLocked(false)
                .build();

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // When
        UserResponse response = authService.register(request);

        // Then
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.getId()).isNotNull();

        // Verify save called once
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getPasswordHash()).isEqualTo("hashedPassword");
    }


    @Test
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        given(userRepository.existsByEmail("test@example.com")).willReturn(true);

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }
}
