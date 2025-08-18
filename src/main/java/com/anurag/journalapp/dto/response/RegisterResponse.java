package com.anurag.journalapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}