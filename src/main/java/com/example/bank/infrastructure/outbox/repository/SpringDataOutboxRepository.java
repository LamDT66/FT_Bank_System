package com.example.bank.infrastructure.outbox.repository;

import com.example.bank.infrastructure.outbox.entity.OutboxJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataOutboxRepository extends JpaRepository<OutboxJpaEntity, String> {
    List<OutboxJpaEntity> findTop100ByStatusOrderByCreatedAtAsc(String status);
}
