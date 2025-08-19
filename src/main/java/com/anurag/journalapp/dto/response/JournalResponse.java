package com.anurag.journalapp.dto.response;

import com.anurag.journalapp.enums.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@Data
@Getter
@Setter
public class JournalResponse {
    String id;

    private String title;

    private String content;

    private LocalDate entryDate;


    private Instant createdAt;

    private Instant updatedAt;


    private Integer wordCount;          // compute on save/update


    private Double moodScore;           // -1..1 or 0..1 (decide convention)


    private List<String> tags;          // e.g., ["gratitude","work","health"]


    private Visibility visibility;

    private boolean archived;
}
