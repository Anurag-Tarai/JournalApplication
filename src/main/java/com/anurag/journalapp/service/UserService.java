package com.anurag.journalapp.service;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> update(User user, String userName) {
        User userInDb = userRepository.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userRepository.save(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
