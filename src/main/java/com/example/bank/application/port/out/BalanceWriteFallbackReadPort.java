package com.example.bank.application.port.out;

import com.example.bank.application.dto.AccountBalanceDto;

import java.util.Optional;

public interface BalanceWriteFallbackReadPort {
    Optional<AccountBalanceDto> findBalanceFromWriteDb(String accountId);
}
