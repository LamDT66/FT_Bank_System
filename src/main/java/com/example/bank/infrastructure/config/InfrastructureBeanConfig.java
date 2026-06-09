package com.example.bank.infrastructure.config;

import com.example.bank.application.port.out.*;
import com.example.bank.infrastructure.outbox.adapter.OutboxStoreAdapter;
import com.example.bank.infrastructure.outbox.repository.SpringDataOutboxRepository;
import com.example.bank.infrastructure.read.mongo.adapter.MongoBalanceReadAdapter;
import com.example.bank.infrastructure.read.mongo.repository.AccountBalanceMongoRepository;
import com.example.bank.infrastructure.redis.RedisRecentWriteFlagAdapter;
import com.example.bank.infrastructure.write.jpa.adapter.AccountWriteRepositoryAdapter;
import com.example.bank.infrastructure.write.jpa.repository.SpringDataAccountJpaRepository;
import com.example.bank.infrastructure.write.query.MySqlBalanceFallbackReadAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class InfrastructureBeanConfig {

    @Bean
    public AccountWriteRepositoryPort accountWriteRepositoryPort(
            SpringDataAccountJpaRepository repository
    ) {
        return new AccountWriteRepositoryAdapter(repository);
    }

    @Bean
    public OutboxStorePort outboxStorePort(
            SpringDataOutboxRepository repository,
            ObjectMapper objectMapper
    ) {
        return new OutboxStoreAdapter(repository, objectMapper);
    }

    @Bean
    public RecentWriteFlagPort recentWriteFlagPort(
            StringRedisTemplate redisTemplate
    ) {
        return new RedisRecentWriteFlagAdapter(redisTemplate);
    }

    @Bean
    public BalanceReadPort balanceReadPort(
            AccountBalanceMongoRepository repository
    ) {
        return new MongoBalanceReadAdapter(repository);
    }

    @Bean
    public BalanceWriteFallbackReadPort balanceWriteFallbackReadPort(
            NamedParameterJdbcTemplate jdbcTemplate
    ) {
        return new MySqlBalanceFallbackReadAdapter(jdbcTemplate);
    }
}
