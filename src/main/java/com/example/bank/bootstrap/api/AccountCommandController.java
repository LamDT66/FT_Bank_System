package com.example.bank.bootstrap.api;

import com.example.bank.application.command.DepositMoneyCommand;
import com.example.bank.application.command.WithdrawMoneyCommand;
import com.example.bank.application.port.in.DepositMoneyUseCase;
import com.example.bank.application.port.in.WithdrawMoneyUseCase;
import com.example.bank.bootstrap.api.request.DepositRequest;
import com.example.bank.bootstrap.api.request.WithdrawRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountCommandController {

    private final DepositMoneyUseCase depositMoneyUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;

    public AccountCommandController(
            DepositMoneyUseCase depositMoneyUseCase,
            WithdrawMoneyUseCase withdrawMoneyUseCase
    ) {
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable String accountId,
            @RequestBody DepositRequest request
    ) {
        depositMoneyUseCase.deposit(new DepositMoneyCommand(accountId, request.amount()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable String accountId,
            @RequestBody WithdrawRequest request
    ) {
        withdrawMoneyUseCase.withdraw(new WithdrawMoneyCommand(accountId, request.amount()));
        return ResponseEntity.ok().build();
    }
}
