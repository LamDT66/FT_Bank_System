package com.example.bank.infrastructure.read.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "processed_event_log")
public class ProcessedEventDocument {

    @Id
    private String eventId;

    private Instant processedAt;

    protected ProcessedEventDocument() {
    }

    public ProcessedEventDocument(String eventId, Instant processedAt) {
        this.eventId = eventId;
        this.processedAt = processedAt;
    }

    public String getEventId() {
        return eventId;
    }
}
