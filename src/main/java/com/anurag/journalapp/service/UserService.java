package com.anurag.journalapp.service;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JournalEntryRepo journalEntryRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<User> save(User user) {
        String name = user.getUserName();
        User newUser = userRepository.findByUserName(name);
        //if username is already exist
        if (newUser != null) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        // if user does not exist
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<?> update(User user, String userName) {
        User userInDb = userRepository.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userInDb);
        return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> delete(String userName) {
        User userInDb = userRepository.findByUserName(userName);
        List<Journal> all = userRepository.findByUserName(userName).getJouranlLists();
        for (Journal journal : all) {
            if (journalEntryRepo.existsById(journal.getId())) {
                journalEntryRepo.delete(journal);
            }
        }
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN"));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
