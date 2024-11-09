package com.anurag.journalapp.service;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<Journal> addJournal(Journal journal, String userName){
       try{
           User userINdb = userRepository.findByUserName(userName);
           journal.setDate(LocalDateTime.now());
           Journal save = journalEntryRepo.save(journal);
           userINdb.getJouranlLists().add(save);
           userRepository.save(userINdb);
           return new ResponseEntity<>(journal,HttpStatus.CREATED);
       } catch (Exception e) {
           System.out.println(e);
           throw new RuntimeException("An error occurred while saving the entry", e);
//           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<List<Journal>> findJournal(String userName){
        List<Journal> all = userRepository.findByUserName(userName).getJouranlLists();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Journal> findJournalById(ObjectId myId){
        Optional<Journal> journalEntry = journalEntryRepo.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> removeJournalById(String userName, ObjectId myId) {
        User userIndb = userRepository.findByUserName(userName);
        if (journalEntryRepo.existsById(myId)){
            journalEntryRepo.deleteById(myId);
            userIndb.getJouranlLists().removeIf(x->x.getId().equals(myId));
            userRepository.save(userIndb);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateJournalById(Journal newJournal, ObjectId myId) {
        Journal oldJournal = journalEntryRepo.findById(myId).orElse(null);
        if(oldJournal!=null){
            oldJournal.setContent(newJournal.getContent()!=null&&!newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
            oldJournal.setTitle(newJournal.getTitle()!=null&& !newJournal.getTitle().isEmpty() ? newJournal.getTitle() : oldJournal.getTitle());
            journalEntryRepo.save(oldJournal);
            return new ResponseEntity<>(oldJournal,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Journal> all_journal() {
        return journalEntryRepo.findAll();
    }
}
