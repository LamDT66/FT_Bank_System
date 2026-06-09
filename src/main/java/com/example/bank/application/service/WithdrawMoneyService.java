package com.example.bank.application.service;

import com.example.bank.application.command.WithdrawMoneyCommand;
import com.example.bank.application.port.in.WithdrawMoneyUseCase;
import com.example.bank.application.port.out.AccountWriteRepositoryPort;
import com.example.bank.application.port.out.OutboxStorePort;
import com.example.bank.application.port.out.RecentWriteFlagPort;
import com.example.bank.domain.model.Account;
import com.example.bank.domain.model.AccountId;
import com.example.bank.domain.model.Money;

import java.time.Duration;

public class WithdrawMoneyService implements WithdrawMoneyUseCase {

    private final AccountWriteRepositoryPort accountWriteRepositoryPort;
    private final OutboxStorePort outboxStorePort;
    private final RecentWriteFlagPort recentWriteFlagPort;

    public WithdrawMoneyService(
            AccountWriteRepositoryPort accountWriteRepositoryPort,
            OutboxStorePort outboxStorePort,
            RecentWriteFlagPort recentWriteFlagPort
    ) {
        this.accountWriteRepositoryPort = accountWriteRepositoryPort;
        this.outboxStorePort = outboxStorePort;
        this.recentWriteFlagPort = recentWriteFlagPort;
    }

    @Override
    public void withdraw(WithdrawMoneyCommand command) {
        AccountId accountId = AccountId.of(command.accountId());

        Account account = accountWriteRepositoryPort.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        var event = account.withdraw(Money.of(command.amount()));

        accountWriteRepositoryPort.save(account);
        outboxStorePort.save(event);

        recentWriteFlagPort.markRecent(command.accountId(), Duration.ofSeconds(3));
    }
}
