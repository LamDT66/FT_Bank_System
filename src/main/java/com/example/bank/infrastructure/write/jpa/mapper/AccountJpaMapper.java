package com.example.bank.infrastructure.write.jpa.mapper;

import com.example.bank.domain.model.Account;
import com.example.bank.domain.model.AccountId;
import com.example.bank.domain.model.Money;
import com.example.bank.infrastructure.write.jpa.entity.AccountJpaEntity;

public class AccountJpaMapper {

    public static Account toDomain(AccountJpaEntity entity) {
        return Account.reconstitute(
                AccountId.of(entity.getId()),
                Money.of(entity.getBalance()),
                entity.getVersion()
        );
    }

    public static AccountJpaEntity toEntity(Account account) {
        return new AccountJpaEntity(
                account.id().value(),
                account.balance().value(),
                account.version()
        );
    }
}
