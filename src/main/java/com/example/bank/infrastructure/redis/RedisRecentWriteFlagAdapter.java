package com.example.bank.infrastructure.redis;

import com.example.bank.application.port.out.RecentWriteFlagPort;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

public class RedisRecentWriteFlagAdapter implements RecentWriteFlagPort {

    private final StringRedisTemplate redisTemplate;

    public RedisRecentWriteFlagAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void markRecent(String accountId, Duration ttl) {
        String key = buildKey(accountId);
        redisTemplate.opsForValue().set(key, "1", ttl);
    }

    @Override
    public boolean exists(String accountId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(buildKey(accountId)));
    }

    private String buildKey(String accountId) {
        return "balance:recent-write:" + accountId;
    }
}
