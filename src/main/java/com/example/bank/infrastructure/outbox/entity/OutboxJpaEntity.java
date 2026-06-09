package com.example.bank.infrastructure.outbox.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxJpaEntity {

    @Id
    private String id;

    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    protected OutboxJpaEntity() {
    }

    public OutboxJpaEntity(
            String id,
            String aggregateId,
            String eventType,
            String payload,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void markProcessed() {
        this.status = "PROCESSED";
        this.processedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }
}
