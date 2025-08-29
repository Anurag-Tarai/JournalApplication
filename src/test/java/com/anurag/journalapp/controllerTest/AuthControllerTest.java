package com.anurag.journalapp.controllerTest;


import com.anurag.journalapp.controller.AuthController;
import com.anurag.journalapp.dto.request.AuthRequest;
import com.anurag.journalapp.dto.request.RegisterRequest;
import com.anurag.journalapp.dto.response.UserResponse;
import com.anurag.journalapp.security.JwtUtils;
import com.anurag.journalapp.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private Authentication authentication;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MongoDatabaseFactory mongoDatabaseFactory; // 👈 add this
    @MockBean
    private MongoTransactionManager mongoTransactionManager; // 👈 add this too

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())   // disable csrf if you don’t want to add tokens
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // allow all
                    .build();
        }
    }

    @Nested
    @DisplayName("Register Endpoint Tests")
    class RegisterTests {

        @Test
        void shouldRegisterUserSuccessfully() throws Exception {
            RegisterRequest request = RegisterRequest.builder()
                    .email("test@example.com")
                    .password("password123")
                    .firstName("John")
                    .lastName("Doe")
                    .build();

            UserResponse response = UserResponse.builder()
                    .id("abc123")
                    .email("test@example.com")
                    .firstName("John")
                    .lastName("Doe")
                    .build();

            given(authService.register(any(RegisterRequest.class))).willReturn(response);

            mockMvc.perform(post("/api/auth/register")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value("abc123"))
                    .andExpect(jsonPath("$.email").value("test@example.com"))
                    .andExpect(jsonPath("$.firstName").value("John"))
                    .andExpect(jsonPath("$.lastName").value("Doe"));
        }
    }

    @Nested
    @DisplayName("Login Endpoint Tests")
    class LoginTests {

        @Test
        void shouldLoginUserSuccessfully() throws Exception {
            AuthRequest loginRequest = AuthRequest.builder()
                    .email("test@example.com")
                    .password("password123")
                    .build();

            String token = "jwt.token.here";
            given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .willReturn(authentication);

            // Mock JWT generation if you use a service; else skip
//            given(jwtUtils.generateJwtToken(authentication)).willReturn(token);

            mockMvc.perform(post("/api/auth/login")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk());
        }
    }
}

