package com.example.bank.infrastructure.outbox.adapter;

import com.example.bank.application.port.out.OutboxStorePort;
import com.example.bank.domain.event.DomainEvent;
import com.example.bank.infrastructure.outbox.entity.OutboxJpaEntity;
import com.example.bank.infrastructure.outbox.repository.SpringDataOutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class OutboxStoreAdapter implements OutboxStorePort {

    private final SpringDataOutboxRepository repository;
    private final ObjectMapper objectMapper;

    public OutboxStoreAdapter(SpringDataOutboxRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(DomainEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxJpaEntity outbox = new OutboxJpaEntity(
                    event.eventId(),
                    event.aggregateId(),
                    event.eventType(),
                    payload,
                    "PENDING",
                    LocalDateTime.now()
            );
            repository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize domain event", e);
        }
    }
}
