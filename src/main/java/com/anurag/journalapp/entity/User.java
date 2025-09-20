package com.anurag.journalapp.entity;


import com.anurag.journalapp.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;

@Builder
@Data
@Document(collection = "users")
public class User {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String email;

    private String firstName;
    private String lastName;

    // Only store hash, never return in API
    @JsonIgnore
    private String passwordHash;

    private boolean sentimentAnalysis;

    @Builder.Default
    private Set<UserRole> roles = EnumSet.of(UserRole.USER);

    @Builder.Default
    private boolean enabled = true; // account status (active/inactive) e.g - email is not verified

    @Builder.Default
    private boolean accountLocked = false; // security lock (due to suspicious activity or admin action) e.g - lock due to too many login tries

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
