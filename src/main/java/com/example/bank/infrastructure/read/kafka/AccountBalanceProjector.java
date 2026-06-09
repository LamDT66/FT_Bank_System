package com.example.bank.infrastructure.read.kafka;

import com.example.bank.infrastructure.read.mongo.document.AccountBalanceDocument;
import com.example.bank.infrastructure.read.mongo.document.ProcessedEventDocument;
import com.example.bank.infrastructure.read.mongo.repository.AccountBalanceMongoRepository;
import com.example.bank.infrastructure.read.mongo.repository.ProcessedEventMongoRepository;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Component
public class AccountBalanceProjector {

    private final AccountBalanceMongoRepository balanceRepository;
    private final ProcessedEventMongoRepository processedEventRepository;
    private final ObjectMapper objectMapper;

    public AccountBalanceProjector(
            AccountBalanceMongoRepository balanceRepository,
            ProcessedEventMongoRepository processedEventRepository,
            ObjectMapper objectMapper
    ) {
        this.balanceRepository = balanceRepository;
        this.processedEventRepository = processedEventRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "bank-account-events", groupId = "balance-read-model-group-v5")
    public void onMessage(String payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);

            if (root.isTextual()) {
                root = objectMapper.readTree(root.asText());
            }

            String eventId = root.path("eventId").asText(null);
            String aggregateId = root.path("aggregateId").asText(null);

            if (eventId == null || aggregateId == null) {
                log.error("Invalid payload: missing eventId or aggregateId. Payload={}", payload);
                return;
            }

            if (processedEventRepository.existsById(eventId)) {
                log.info("Skip already processed eventId={}", eventId);
                return;
            }

            BigDecimal newBalance = root.path("newBalance").decimalValue();
            long version = root.path("version").asLong();
            Instant occurredAt = Instant.parse(root.path("occurredAt").asText());

            balanceRepository.save(
                    new AccountBalanceDocument(
                            aggregateId,
                            newBalance,
                            version,
                            occurredAt
                    )
            );

            processedEventRepository.save(
                    new ProcessedEventDocument(eventId, Instant.now())
            );

            log.info("Mongo updated successfully for aggregateId={}, eventId={}", aggregateId, eventId);

        } catch (Exception e) {
            log.error("Failed to process Kafka payload={}", payload, e);
        }
    }
}
