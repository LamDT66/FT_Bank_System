package com.example.bank.domain.event;

import java.math.BigDecimal;
import java.time.Instant;

public record MoneyDepositedEvent(
        String eventId,
        String aggregateId,
        BigDecimal amount,
        BigDecimal newBalance,
        long version,
        Instant occurredAt
) implements DomainEvent {

    @Override
    public String eventType() {
        return "MoneyDeposited";
    }
}
