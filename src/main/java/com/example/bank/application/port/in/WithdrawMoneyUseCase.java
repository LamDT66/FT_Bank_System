package com.example.bank.application.port.in;

import com.example.bank.application.command.WithdrawMoneyCommand;

public interface WithdrawMoneyUseCase {
    void withdraw(WithdrawMoneyCommand command);
}
