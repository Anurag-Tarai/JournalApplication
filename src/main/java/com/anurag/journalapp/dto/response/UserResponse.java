package com.anurag.journalapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}