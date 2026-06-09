package com.example.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class AccountId {
    private final String value;

    private AccountId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AccountId must not be blank");
        }
        this.value = value;
    }

    public static AccountId of(String value) {
        return new AccountId(value);
    }

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID().toString());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccountId other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
