package com.example.bank.infrastructure.outbox.scheduler;

import com.example.bank.infrastructure.outbox.entity.OutboxJpaEntity;
import com.example.bank.infrastructure.outbox.repository.SpringDataOutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;


@Slf4j
@Component
public class OutboxScheduler {

    private final SpringDataOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxScheduler(
            SpringDataOutboxRepository outboxRepository,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void publishPendingEvents() {
        log.info(">>> OutboxScheduler is running...");
        List<OutboxJpaEntity> pendingEvents = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc("PENDING");

        for (OutboxJpaEntity event : pendingEvents) {
            kafkaTemplate.send("bank-account-events", event.getPayload());
            event.markProcessed();
            outboxRepository.save(event);
        }
    }
}
