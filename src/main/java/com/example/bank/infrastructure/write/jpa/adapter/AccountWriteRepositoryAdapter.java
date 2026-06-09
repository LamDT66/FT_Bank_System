package com.example.bank.infrastructure.write.jpa.adapter;

import com.example.bank.application.port.out.AccountWriteRepositoryPort;
import com.example.bank.domain.model.Account;
import com.example.bank.domain.model.AccountId;
import com.example.bank.infrastructure.write.jpa.mapper.AccountJpaMapper;
import com.example.bank.infrastructure.write.jpa.repository.SpringDataAccountJpaRepository;

import java.util.Optional;

public class AccountWriteRepositoryAdapter implements AccountWriteRepositoryPort {

    private final SpringDataAccountJpaRepository repository;

    public AccountWriteRepositoryAdapter(SpringDataAccountJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {
        return repository.findById(accountId.value()).map(AccountJpaMapper::toDomain);
    }

    @Override
    public void save(Account account) {
        repository.save(AccountJpaMapper.toEntity(account));
    }
}
