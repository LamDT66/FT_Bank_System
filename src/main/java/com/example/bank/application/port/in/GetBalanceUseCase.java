package com.example.bank.application.port.in;

import com.example.bank.application.dto.AccountBalanceDto;
import com.example.bank.application.query.GetBalanceQuery;

public interface GetBalanceUseCase {
    AccountBalanceDto getBalance(GetBalanceQuery query);
}
