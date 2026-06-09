package com.example.bank.application.service;

import com.example.bank.application.dto.AccountBalanceDto;
import com.example.bank.application.port.in.GetBalanceUseCase;
import com.example.bank.application.port.out.BalanceReadPort;
import com.example.bank.application.port.out.BalanceWriteFallbackReadPort;
import com.example.bank.application.port.out.RecentWriteFlagPort;
import com.example.bank.application.query.GetBalanceQuery;

public class GetBalanceService implements GetBalanceUseCase {

    private final RecentWriteFlagPort recentWriteFlagPort;
    private final BalanceWriteFallbackReadPort balanceWriteFallbackReadPort;
    private final BalanceReadPort balanceReadPort;

    public GetBalanceService(
            RecentWriteFlagPort recentWriteFlagPort,
            BalanceWriteFallbackReadPort balanceWriteFallbackReadPort,
            BalanceReadPort balanceReadPort
    ) {
        this.recentWriteFlagPort = recentWriteFlagPort;
        this.balanceWriteFallbackReadPort = balanceWriteFallbackReadPort;
        this.balanceReadPort = balanceReadPort;
    }

    @Override
    public AccountBalanceDto getBalance(GetBalanceQuery query) {
        boolean recent = recentWriteFlagPort.exists(query.accountId());

        if (recent) {
            return balanceWriteFallbackReadPort.findBalanceFromWriteDb(query.accountId())
                    .orElseThrow(() -> new IllegalArgumentException("Account not found in write db"));
        }

        return balanceReadPort.findBalance(query.accountId())
                .orElseGet(() -> balanceWriteFallbackReadPort.findBalanceFromWriteDb(query.accountId())
                        .orElseThrow(() -> new IllegalArgumentException("Account not found")));
    }
}
