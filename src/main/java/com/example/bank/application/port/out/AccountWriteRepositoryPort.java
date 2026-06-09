package com.example.bank.application.port.out;

import com.example.bank.domain.model.Account;
import com.example.bank.domain.model.AccountId;

import java.util.Optional;

public interface AccountWriteRepositoryPort {
    Optional<Account> findById(AccountId accountId);
    void save(Account account);
}
