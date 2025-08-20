package com.anurag.journalapp.dto.request;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.enums.Visibility;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class JournalUpdateRequest {
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @Size(max = 20000, message = "Content must be less than 20000 characters")
    private String content;

    private LocalDate entryDate;

    private Visibility visibility;

    private Boolean archived;
}
