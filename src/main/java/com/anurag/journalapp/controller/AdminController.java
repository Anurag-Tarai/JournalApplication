//package com.anurag.journalapp.controller;
//
//import com.anurag.journalapp.entity.User;
//import com.anurag.journalapp.repository.UserRepository;
//import com.anurag.journalapp.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    AuthService userService;
//
//    @GetMapping("/all-users")
//    public ResponseEntity<?> getAllUsers(){
//        List<User> all = userRepository.findAll();
//        if(all!=null&&!all.isEmpty()){
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//    @PostMapping("/create-admin")
//    public ResponseEntity<?> creatAdmin(@RequestBody User user){
//        return userService.saveAdmin(user);
//    }
//}
