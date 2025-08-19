package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.request.JournalCreateRequest;
import com.anurag.journalapp.dto.response.JournalResponse;
import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.enums.Visibility;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JournalEntryService {

    final private JournalEntryRepo journalEntryRepo;

    final private UserRepository userRepository;

    public JournalResponse add(JournalCreateRequest request, ObjectId userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        // Normalize and validate inputs
        String title = request.getTitle().trim();
        String content = request.getContent() != null ? request.getContent().trim() : "";

        //  Set defaults
        LocalDate entryDate = request.getEntryDate() != null ?
                request.getEntryDate() : LocalDate.now();

        Visibility visibility = request.getVisibility() != null ?
                request.getVisibility() : Visibility.PRIVATE;

        // Build entity
        Journal journal = Journal.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .entryDate(entryDate)
                .visibility(visibility)
                .archived(false)
                .build();

        // Save entity
        Journal saved = journalEntryRepo.save(journal);

        // Build and return response
        return mapToResponse(saved);
    }

    private JournalResponse mapToResponse(Journal journal) {
        JournalResponse response = new JournalResponse();
        response.setId(journal.getId().toHexString());
        response.setTitle(journal.getTitle());
        response.setContent(journal.getContent());
        response.setEntryDate(journal.getEntryDate());
        response.setCreatedAt(journal.getCreatedAt());
        response.setUpdatedAt(journal.getUpdatedAt());

        // Compute word count
        Integer wordCount = (journal.getContent() == null || journal.getContent().isBlank())
                ? 0
                : journal.getContent().trim().split("\\s+").length;
        response.setWordCount(wordCount);

        // Set other fields
        response.setMoodScore(journal.getMoodScore()); // null for now
        response.setTags(journal.getTags() != null ? journal.getTags() : List.of());
        response.setVisibility(journal.getVisibility());
        response.setArchived(journal.isArchived());

        return response;
    }

}
