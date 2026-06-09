package com.example.bank.infrastructure.write.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class AccountJpaEntity {

    @Id
    private String id;

    private BigDecimal balance;

    private Long version;

    protected AccountJpaEntity() {
    }

    public AccountJpaEntity(String id, BigDecimal balance, Long version) {
        this.id = id;
        this.balance = balance;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }
}
