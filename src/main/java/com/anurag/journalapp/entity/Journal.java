package com.anurag.journalapp.entity;


import com.anurag.journalapp.enums.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "journals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CompoundIndex(name = "user_created_idx", def = "{'userId': 1, 'createdAt': -1}")
public class Journal {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    // Owner of the entry (link to users._id)
    @Indexed
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;

    @NonNull
    private String title;

    private String content;

    // Logical date of the entry for calendar-based features
    // Defaults to today if not provided in service layer
    @Indexed
    private LocalDate entryDate;

    // Auditing timestamps
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private List<String> tags;          // e.g., ["gratitude","work","health"]

    // Optional metadata for future features

    @Transient
    private Integer wordCount;          // compute on save/update

    @Transient
    private Double moodScore;           // -1..1 or 0..1 (decide convention)

    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    @Builder.Default
    private boolean archived = false;
}
