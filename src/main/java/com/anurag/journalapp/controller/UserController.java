package com.anurag.journalapp.controller;

import com.anurag.journalapp.dto.response.QuoteResponse;
import com.anurag.journalapp.dto.response.UserResponse;
import com.anurag.journalapp.dto.response.WheatherApiResponse;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.repository.UserRepository;
import com.anurag.journalapp.service.AuthService;
import com.anurag.journalapp.service.ExternalAPIService;
import com.anurag.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthService authService;


    @Autowired
    private UserService userService;

    @Autowired
    private ExternalAPIService externalAPIService;

    @GetMapping("greet")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email is username

        UserResponse userResponse = userService.getUserDetails(email);
        WheatherApiResponse wheather = externalAPIService.getWheather("Mumbai");
        QuoteResponse quote = externalAPIService.getQuote();

        StringBuilder greeting = new StringBuilder("Hi " + userResponse.getFirstName());

        if (wheather != null) {
            greeting.append(", Today Feels like ")
                    .append(wheather.getCurrent().getFeelslike())
                    .append(" Degree Celsius");
        }

        if (quote != null) {
            greeting.append("\n\nHere is a quote for you:\n")
                    .append("\"").append(quote.getQuote()).append("\"")
                    .append(" — ").append(quote.getAuthor());
        }

        return ResponseEntity.ok(greeting.toString());
    }


}
