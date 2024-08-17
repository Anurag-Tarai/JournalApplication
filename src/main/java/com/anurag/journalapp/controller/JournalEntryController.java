package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.JournalEntry;
import com.anurag.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @PostMapping("/add")
    public boolean addJournal(@RequestBody JournalEntry journal){
       return journalEntryService.addJournal(journal);
    }
    @GetMapping("get/all")
      public List<JournalEntry> getJournals(){
        return journalEntryService.findJournal();
    }

    @GetMapping("get/{myid}")
    public JournalEntry getJournalById(@PathVariable ObjectId myid){
        return journalEntryService.findJournalById(myid);
    }

    @DeleteMapping("delete/{myId}")
    public boolean deleteJournal(@PathVariable ObjectId myId){
        return journalEntryService.removeJournalById(myId);
    }

    @PutMapping("update/{myId}")
    public JournalEntry updateJournal(@RequestBody JournalEntry journal, @PathVariable ObjectId myId){
        return journalEntryService.updateJournalById(journal, myId);
    }

}
