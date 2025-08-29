package com.anurag.journalapp.entity;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightData {
    // Basic Statistics
    private int totalEntries;
    private int totalWords;
    private double avgWordsPerEntry;
    private int daysWithEntries;
    private double consistencyScore; // 0 to 1

    // Sentiment Analysis (simple lexicon-based)
    private double avgSentimentScore; // -1 to 1
    private String dominantMood; // POSITIVE, NEGATIVE, NEUTRAL
    private Map<String, Integer> moodDistribution;

    // Content Analysis
    private List<String> topKeywords;
    private List<String> topTags;
    private List<String> frequentTopics;

    // Generated Insights
    private List<String> insights;
    private List<String> patterns;
    private List<String> recommendations;

    // Trends (compared to previous period)
    private Double sentimentTrend; // null if no previous data
    private Double wordCountTrend;
    private Double consistencyTrend;
}
