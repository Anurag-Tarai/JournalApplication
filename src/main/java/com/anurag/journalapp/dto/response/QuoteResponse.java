package com.anurag.journalapp.dto.response;

import lombok.Data;

@Data
public class QuoteResponse {
    private String quote;
    private String author;
    private String category;
}
