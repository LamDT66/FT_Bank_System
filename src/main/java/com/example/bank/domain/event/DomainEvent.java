package com.example.bank.domain.event;

import java.time.Instant;

public interface DomainEvent {
    String eventId();
    String aggregateId();
    String eventType();
    Instant occurredAt();
}
