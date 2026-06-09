package com.example.bank.application.port.in;

import com.example.bank.application.command.DepositMoneyCommand;

public interface DepositMoneyUseCase {
    void deposit(DepositMoneyCommand command);
}
