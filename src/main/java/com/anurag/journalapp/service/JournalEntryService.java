package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.request.JournalCreateRequest;
import com.anurag.journalapp.dto.request.JournalUpdateRequest;
import com.anurag.journalapp.dto.response.JournalResponse;
import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.enums.Sentiment;
import com.anurag.journalapp.enums.Visibility;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JournalEntryService {

    final private JournalEntryRepo journalEntryRepo;

    final private UserRepository userRepository;

    public JournalResponse create(JournalCreateRequest request, ObjectId userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        // Database normalization : is about structuring data storage.
        // Input normalization : is about cleaning data values before storing them.

        // Normalize and validate inputs
        // Normalization here refers to cleaning (e.g., trimming spaces, handling nulls) and standardizing user input to maintain consistency and avoid bad data in the database.
        String title = request.getTitle().trim();
        String content = request.getContent() != null ? request.getContent().trim() : "";

        //  Set defaults
        LocalDate entryDate = request.getEntryDate() != null ?
                request.getEntryDate() : LocalDate.now();

        Visibility visibility = request.getVisibility() != null ?
                request.getVisibility() : Visibility.PRIVATE;
        Sentiment sentiment = request.getSentiment()!= null? request.getSentiment():Sentiment.HAPPY;

        // Build entity
        Journal journal = Journal.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .entryDate(entryDate)
                .visibility(visibility)
                .sentiment(sentiment)
                .archived(false)
                .build();

        // Save entity
        Journal saved = journalEntryRepo.save(journal);

        // Build and return response
        return mapToResponse(saved);
    }
    /**
     * List active journal entries for a user, optionally filtered by search term.
     * @param userId ID of the authenticated user
     * @param search optional search string for title/content
     * @param pageable pagination and sort information
     * @return paginated response DTOs
     */
    public Page<JournalResponse> list(ObjectId userId, String search, Pageable pageable) {
        Page<Journal> page = StringUtils.hasText(search)
                // Search title or content (case-insensitive)
                ? journalEntryRepo.findByUserIdAndArchivedFalseAndTitleContainingIgnoreCaseOrUserIdAndArchivedFalseAndContentContainingIgnoreCase(
                userId, search.trim(), userId, search.trim(), pageable)
                // Default: list non-archived entries
                : journalEntryRepo.findByUserIdAndArchivedFalse(userId, pageable);
        return page.map(this::mapToResponse);
    }

    /**
     * Get a single journal entry by ID for the given user.
     * @param userId ID of the authenticated user
     * @param journalId ID of the journal entry
     * @return the entry as a response DTO
     */
    public JournalResponse get(ObjectId userId, ObjectId journalId) {
        Journal journal = journalEntryRepo.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Journal not found"));
        return mapToResponse(journal);
    }

    /**
     * Update an existing journal entry for the given user.
     * Only non-null fields in the request are updated.
     * @param userId ID of the authenticated user
     * @param journalId ID of the journal entry
     * @param req DTO containing updated fields
     * @return the updated entry as a response DTO
     * @throws IllegalArgumentException if not found or not owned by user
     */
    public JournalResponse update(ObjectId userId, ObjectId journalId, JournalUpdateRequest req) {
        Journal journal = journalEntryRepo.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Journal not found"));

        // Apply updates if present
        if (StringUtils.hasText(req.getTitle())) {
            journal.setTitle(req.getTitle().trim());
        }
        if (req.getContent() != null) {
            journal.setContent(req.getContent().trim());
        }
        if (req.getEntryDate() != null) {
            journal.setEntryDate(req.getEntryDate());
        }
        if (req.getVisibility() != null) {
            journal.setVisibility(req.getVisibility());
        }

        // Save and map to DTO
        Journal updated = journalEntryRepo.save(journal);
        return mapToResponse(updated);
    }

    /**
     * Soft-delete (archive) a journal entry for the given user.
     * @param userId ID of the authenticated user
     * @param journalId ID of the journal entry
     * @throws IllegalArgumentException if not found or not owned by user
     */
    public void archive(ObjectId userId, ObjectId journalId) {
        Journal journal = journalEntryRepo.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Journal not found"));
        journal.setArchived(true);
        journalEntryRepo.save(journal);
    }


    // Hard-delete a journal
    public void delete(ObjectId userId, ObjectId journalId) {
        Journal j = journalEntryRepo.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Journal not found"));
        journalEntryRepo.delete(j);
    }

    // Helper: map entity to response DTO
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
        response.setSentiment(journal.getSentiment()); // null for now
        response.setTags(journal.getTags() != null ? journal.getTags() : List.of());
        response.setVisibility(journal.getVisibility());
        response.setArchived(journal.isArchived());

        return response;
    }

}
