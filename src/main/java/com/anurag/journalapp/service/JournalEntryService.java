package com.anurag.journalapp.service;

import com.anurag.journalapp.entity.JournalEntry;
import com.anurag.journalapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalEntryRepo;

    public boolean addJournal(JournalEntry journal){
        journal.setDate(LocalDateTime.now());
        journalEntryRepo.save(journal);
        return true;
    }

    public List<JournalEntry> findJournal(){
        return journalEntryRepo.findAll();
    }

    public JournalEntry findJournalById(ObjectId myId){
        return journalEntryRepo.findById(myId).orElse(null);
    }

    public boolean removeJournalById(ObjectId myId) {
        if (journalEntryRepo.existsById(myId)) {
            journalEntryRepo.deleteById(myId);
            return true;
        }
        return false;
    }

    public JournalEntry updateJournalById(JournalEntry newJournal, ObjectId myId) {
        JournalEntry oldJournal = journalEntryRepo.findById(myId).orElse(null);
        if(oldJournal!=null){
            oldJournal.setContent(newJournal.getContent()!=null&&!newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
            oldJournal.setTitle(newJournal.getTitle()!=null&&!newJournal.getTitle().equals("")? newJournal.getTitle() : oldJournal.getTitle());
        }
        journalEntryRepo.save(oldJournal);
        return oldJournal;
    }
}
