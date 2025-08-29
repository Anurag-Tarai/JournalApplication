package com.anurag.journalapp.controller;

import com.anurag.journalapp.dto.request.JournalCreateRequest;
import com.anurag.journalapp.dto.request.JournalUpdateRequest;
import com.anurag.journalapp.dto.response.JournalResponse;
import com.anurag.journalapp.repository.UserRepository;
import com.anurag.journalapp.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PostMapping
    public ResponseEntity<JournalResponse> createJournal(
            @Valid @RequestBody JournalCreateRequest request,
            Authentication authentication) {

        // Get current user ID from authenticated principal
        ObjectId userId = getCurrentUserId(authentication, userRepository );

        JournalResponse response = journalEntryService.create(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // List active journal entries with optional search and pagination.
    @GetMapping
    public ResponseEntity<Page<JournalResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication auth) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<JournalResponse> result = journalEntryService.list(getCurrentUserId(auth,userRepository), search, pageable);
        return ResponseEntity.ok(result);
    }


    // Get a single journal entry by ID.
    @GetMapping("/{id}")
    public ResponseEntity<JournalResponse> get(
            @PathVariable String id,
            Authentication auth) {

        JournalResponse res = journalEntryService.get(getCurrentUserId(auth,userRepository), new ObjectId(id));
        return ResponseEntity.ok(res);
    }

    // Update an existing journal entry.
    @PutMapping("/{id}")
    public ResponseEntity<JournalResponse> update(
            @PathVariable String id,
            @Valid @RequestBody JournalUpdateRequest req,
            Authentication auth) {

        JournalResponse res = journalEntryService.update(getCurrentUserId(auth,userRepository), new ObjectId(id), req);
        return ResponseEntity.ok(res);
    }


    // Soft-delete (archive) a journal entry.
    @DeleteMapping("/{id}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archiveJournal(
            @PathVariable String id,
            Authentication auth) {

        journalEntryService.archive(getCurrentUserId(auth,userRepository), new ObjectId(id));
    }

    // Hard-delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournal(
            @PathVariable String id,
            Authentication authentication) {

        ObjectId userId = getCurrentUserId(authentication, userRepository);
        journalEntryService.delete(userId, new ObjectId(id));
        return ResponseEntity.noContent().build();
    }

}
