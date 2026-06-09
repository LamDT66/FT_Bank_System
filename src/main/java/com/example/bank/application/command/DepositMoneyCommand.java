package com.example.bank.application.command;

import java.math.BigDecimal;

public record DepositMoneyCommand(String accountId, BigDecimal amount) {}
