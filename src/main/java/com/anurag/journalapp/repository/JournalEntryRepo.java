package com.anurag.journalapp.repository;
import com.anurag.journalapp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<Journal, ObjectId> {
}
