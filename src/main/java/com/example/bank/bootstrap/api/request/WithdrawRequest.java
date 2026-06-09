package com.example.bank.bootstrap.api.request;

import java.math.BigDecimal;

public record WithdrawRequest(BigDecimal amount) {}
