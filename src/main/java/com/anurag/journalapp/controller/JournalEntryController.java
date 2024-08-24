package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/{userName}/add")
    public ResponseEntity<Journal> addJournal(@RequestBody Journal journal, @PathVariable String userName){
       return journalEntryService.addJournal(journal, userName);
    }
    @GetMapping("/{userName}/get/all")
      public ResponseEntity<List<Journal>> getJournals(@PathVariable String userName){
        return journalEntryService.findJournal(userName);
    }

    @GetMapping("get/id/{myid}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId myid){
        return journalEntryService.findJournalById(myid);
    }

    @DeleteMapping("{userName}/delete/{myId}")
    public ResponseEntity<?> deleteJournal(@PathVariable String userName,@PathVariable ObjectId myId){
        return journalEntryService.removeJournalById(userName,myId);
    }
    
    @PutMapping("{userName}/update/{myId}")
    public ResponseEntity<?> updateJournal(@RequestBody Journal journal,
                                           @PathVariable ObjectId myId,
                                           @PathVariable String userName)
    {
        return journalEntryService.updateJournalById(journal, myId);
    }
}
