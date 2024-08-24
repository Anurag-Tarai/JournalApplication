package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User add(@RequestBody User user){
     return userService.save(user);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("{userName}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable String userName){
        return userService.update(user, userName);
    }

}
