package com.anurag.journalapp.dto.request;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.enums.Visibility;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class JournalUpdateRequest {
    @Size(max = 200)
    private String title;

    @Size(max = 20000)
    private String content;

    private LocalDate entryDate;

    private Visibility visibility;

    private Boolean archived; // allow toggling archive if you want
}
