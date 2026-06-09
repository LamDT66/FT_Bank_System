package com.example.bank.application.dto;

import java.math.BigDecimal;

public record AccountBalanceDto(
        String accountId,
        BigDecimal balance,
        String source
) {}
