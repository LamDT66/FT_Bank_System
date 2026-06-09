package com.example.bank.infrastructure.read.mongo.adapter;

import com.example.bank.application.dto.AccountBalanceDto;
import com.example.bank.application.port.out.BalanceReadPort;
import com.example.bank.infrastructure.read.mongo.repository.AccountBalanceMongoRepository;

import java.util.Optional;

public class MongoBalanceReadAdapter implements BalanceReadPort {

    private final AccountBalanceMongoRepository repository;

    public MongoBalanceReadAdapter(AccountBalanceMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountBalanceDto> findBalance(String accountId) {
        return repository.findById(accountId)
                .map(doc -> new AccountBalanceDto(
                        doc.getAccountId(),
                        doc.getBalance(),
                        "READ_DB"
                ));
    }
}
