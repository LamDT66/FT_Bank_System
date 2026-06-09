package com.example.bank.application.command;

import java.math.BigDecimal;

public record WithdrawMoneyCommand(String accountId, BigDecimal amount) {}
