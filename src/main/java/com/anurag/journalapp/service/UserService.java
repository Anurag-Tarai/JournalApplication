package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.response.UserResponse;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserDetails(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new UsernameNotFoundException("User Not found with email - "+email);
        return UserResponse.builder()
                .id(String.valueOf(user.get().getId()))
                .email(user.get().getEmail())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName()).build();
    }
}
