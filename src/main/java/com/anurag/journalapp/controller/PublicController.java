package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.service.JournalEntryService;
import com.anurag.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping
    public String health_check(){
        return "OK";
    }
}
