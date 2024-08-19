package com.anurag.journalapp.service;

import com.anurag.journalapp.entity.JournalEntry;
import com.anurag.journalapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalEntryRepo;

    public ResponseEntity<JournalEntry> addJournal(JournalEntry journal){
       try{
           journal.setDate(LocalDateTime.now());
           journalEntryRepo.save(journal);
           return new ResponseEntity<>(journal,HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<List<JournalEntry>> findJournal(){
        List<JournalEntry> all = journalEntryRepo.findAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<JournalEntry> findJournalById(ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryRepo.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> removeJournalById(ObjectId myId) {
        if (journalEntryRepo.existsById(myId)) {
            journalEntryRepo.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateJournalById(JournalEntry newJournal, ObjectId myId) {
        JournalEntry oldJournal = journalEntryRepo.findById(myId).orElse(null);
        if(oldJournal!=null){
            oldJournal.setContent(newJournal.getContent()!=null&&!newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
            oldJournal.setTitle(newJournal.getTitle()!=null&&!newJournal.getTitle().equals("")? newJournal.getTitle() : oldJournal.getTitle());
            journalEntryRepo.save(oldJournal);
            return new ResponseEntity<>(oldJournal,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
