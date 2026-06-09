package com.example.bank.application.port.out;

import java.time.Duration;

public interface RecentWriteFlagPort {
    void markRecent(String accountId, Duration ttl);
    boolean exists(String accountId);
}
