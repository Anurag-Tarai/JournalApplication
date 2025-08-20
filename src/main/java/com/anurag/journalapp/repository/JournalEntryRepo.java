package com.anurag.journalapp.repository;
import com.anurag.journalapp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JournalEntryRepo extends MongoRepository<Journal, ObjectId> {

    // Fetch a single journal ensuring ownership
    Optional<Journal> findByIdAndUserId(ObjectId id, ObjectId userId);

    // List active (non-archived) journals for a user
    Page<Journal> findByUserIdAndArchivedFalse(ObjectId userId, Pageable pageable);

    // Simple search over title OR content (active only)
    // Note: Spring Data “OR” will apply to the two trailing predicates; both are scoped by “archived=false”
    Page<Journal> findByUserIdAndArchivedFalseAndTitleContainingIgnoreCaseOrUserIdAndArchivedFalseAndContentContainingIgnoreCase(
            ObjectId userId1, String titleSearch,
            ObjectId userId2, String contentSearch,
            Pageable pageable);


    // OPTIONAL: list all journals (including archived) for a user (useful if you later add includeArchived)
    Page<Journal> findByUserId(ObjectId userId, Pageable pageable);

    // OPTIONAL: date-range filter for active journals (handy if you add startDate/endDate filters)
    Page<Journal> findByUserIdAndArchivedFalseAndEntryDateBetween(
            ObjectId userId, LocalDate startDate, LocalDate endDate, Pageable pageable
    );
}
