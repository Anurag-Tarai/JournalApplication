package com.anurag.journalapp.entity;

import com.anurag.journalapp.enums.InsightPeriod;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "user_insights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CompoundIndex(
        name = "user_period_start_idx_v2",
        def = "{'userId': 1, 'period': 1, 'periodStart': -1}"
)
public class UserInsight {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @Indexed
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;

    private InsightPeriod period;
    private LocalDate periodStart;
    private LocalDate periodEnd;

    private InsightData data;
    private String aiGeneratedSummary;

    @CreatedDate
    private Instant createdAt;

    private boolean processed;
    private String processingError;
}