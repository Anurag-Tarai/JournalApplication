//package com.anurag.journalapp.controller;
//
//import com.anurag.journalapp.entity.User;
//import com.anurag.journalapp.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//
//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody User user){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        return userService.update(user, userName);
//    }
//
//    @DeleteMapping("/delete_user")
//    public ResponseEntity<?> deleteByUserName(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        return userService.delete(userName);
//    }
//}
