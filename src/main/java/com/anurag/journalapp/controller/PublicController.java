package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public User add(@RequestBody User user){
        return userService.save(user);
    }
}
