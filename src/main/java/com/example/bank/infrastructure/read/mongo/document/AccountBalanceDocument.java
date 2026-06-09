package com.example.bank.infrastructure.read.mongo.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "account_balance_view")
@Getter
public class AccountBalanceDocument {

    @Id
    private String accountId;

    private BigDecimal balance;

    private Long version;

    private Instant updatedAt;

    protected AccountBalanceDocument() {
    }

    public AccountBalanceDocument(String accountId, BigDecimal balance, Long version, Instant updatedAt) {
        this.accountId = accountId;
        this.balance = balance;
        this.version = version;
        this.updatedAt = updatedAt;
    }
}
