package com.example.bank.bootstrap.config;

import com.example.bank.application.port.in.DepositMoneyUseCase;
import com.example.bank.application.port.in.GetBalanceUseCase;
import com.example.bank.application.port.in.WithdrawMoneyUseCase;
import com.example.bank.application.port.out.*;
import com.example.bank.application.service.DepositMoneyService;
import com.example.bank.application.service.GetBalanceService;
import com.example.bank.application.service.WithdrawMoneyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public DepositMoneyUseCase depositMoneyUseCase(
            AccountWriteRepositoryPort accountWriteRepositoryPort,
            OutboxStorePort outboxStorePort,
            RecentWriteFlagPort recentWriteFlagPort
    ) {
        return new DepositMoneyService(accountWriteRepositoryPort, outboxStorePort, recentWriteFlagPort);
    }

    @Bean
    public WithdrawMoneyUseCase withdrawMoneyUseCase(
            AccountWriteRepositoryPort accountWriteRepositoryPort,
            OutboxStorePort outboxStorePort,
            RecentWriteFlagPort recentWriteFlagPort
    ) {
        return new WithdrawMoneyService(accountWriteRepositoryPort, outboxStorePort, recentWriteFlagPort);
    }

    @Bean
    public GetBalanceUseCase getBalanceUseCase(
            RecentWriteFlagPort recentWriteFlagPort,
            BalanceWriteFallbackReadPort balanceWriteFallbackReadPort,
            BalanceReadPort balanceReadPort
    ) {
        return new GetBalanceService(recentWriteFlagPort, balanceWriteFallbackReadPort, balanceReadPort);
    }
}
