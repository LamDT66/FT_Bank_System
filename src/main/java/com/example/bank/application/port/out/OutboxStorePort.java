package com.example.bank.application.port.out;

import com.example.bank.domain.event.DomainEvent;

public interface OutboxStorePort {
    void save(DomainEvent event);
}
