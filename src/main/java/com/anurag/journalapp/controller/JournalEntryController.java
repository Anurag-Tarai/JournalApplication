package com.anurag.journalapp.controller;

import com.anurag.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ArrayList<JournalEntry> getJournals(){
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public Boolean addJournal(@RequestBody JournalEntry journal){
        journalEntries.put(journal.getId(), journal);
        return true;
    }
    @GetMapping("id/{myid}")
    public JournalEntry getJournalById(@PathVariable Long myid){
        return journalEntries.get(myid);
    }
    @PutMapping("/id/{myId}")
    public Boolean updateJournal(@RequestBody JournalEntry journal, @PathVariable Long myId){
        journalEntries.put(myId, journal);
        return true;
    }
    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournal(@PathVariable Long myId){
        return journalEntries.remove(myId);
    }
}
