package com.anurag.journalapp.controller;

import com.anurag.journalapp.dto.request.JournalCreateRequest;
import com.anurag.journalapp.dto.response.JournalResponse;
import com.anurag.journalapp.repository.UserRepository;
import com.anurag.journalapp.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.anurag.journalapp.security.SecurityUtils.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/journals")
public class JournalEntryController {

    final private JournalEntryService journalEntryService;

    final private UserRepository userRepository;

    @PostMapping("/save-journal")
    public ResponseEntity<JournalResponse> createJournal(
            @Valid @RequestBody JournalCreateRequest request,
            Authentication authentication) {

        // Get current user ID from authenticated principal
        ObjectId userId = getCurrentUserId(authentication, userRepository );

        JournalResponse response = journalEntryService.add(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
