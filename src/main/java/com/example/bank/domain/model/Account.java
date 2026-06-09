package com.example.bank.domain.model;

import com.example.bank.domain.event.MoneyDepositedEvent;
import com.example.bank.domain.event.MoneyWithdrawnEvent;
import com.example.bank.domain.exception.InsufficientBalanceException;

import java.time.Instant;
import java.util.UUID;

public class Account {
    private final AccountId id;
    private Money balance;
    private long version;

    private Account(AccountId id, Money balance, long version) {
        this.id = id;
        this.balance = balance;
        this.version = version;
    }

    public static Account open(AccountId accountId) {
        return new Account(accountId, Money.zero(), 0L);
    }

    public static Account reconstitute(AccountId accountId, Money balance, long version) {
        return new Account(accountId, balance, version);
    }

    public MoneyDepositedEvent deposit(Money amount) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        this.balance = this.balance.add(amount);
        this.version++;

        return new MoneyDepositedEvent(
                UUID.randomUUID().toString(),
                this.id.value(),
                amount.value(),
                this.balance.value(),
                this.version,
                Instant.now()
        );
    }

    public MoneyWithdrawnEvent withdraw(Money amount) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero");
        }

        if (this.balance.isLessThan(amount)) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        this.balance = this.balance.subtract(amount);
        this.version++;

        return new MoneyWithdrawnEvent(
                UUID.randomUUID().toString(),
                this.id.value(),
                amount.value(),
                this.balance.value(),
                this.version,
                Instant.now()
        );
    }

    public AccountId id() {
        return id;
    }

    public Money balance() {
        return balance;
    }

    public long version() {
        return version;
    }
}
