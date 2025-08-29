package com.anurag.journalapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping
    public String health_check(){
        return "HEALTH_OK";
    }
}
