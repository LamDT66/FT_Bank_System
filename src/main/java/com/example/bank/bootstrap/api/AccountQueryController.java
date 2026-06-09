package com.example.bank.bootstrap.api;

import com.example.bank.application.port.in.GetBalanceUseCase;
import com.example.bank.application.query.GetBalanceQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountQueryController {

    private final GetBalanceUseCase getBalanceUseCase;

    public AccountQueryController(GetBalanceUseCase getBalanceUseCase) {
        this.getBalanceUseCase = getBalanceUseCase;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(getBalanceUseCase.getBalance(new GetBalanceQuery(accountId)));
    }
}
