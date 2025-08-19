package com.anurag.journalapp.dto.request;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class JournalCreateRequest {
    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 20000)
    private String content;

    // Optional: allow client-specified logical date; default to today if null
    private LocalDate entryDate;

    // Optional: allow visibility selection; default PRIVATE in service if null
    private Visibility visibility;
}