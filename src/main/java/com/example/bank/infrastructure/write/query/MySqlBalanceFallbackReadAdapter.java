package com.example.bank.infrastructure.write.query;

import com.example.bank.application.dto.AccountBalanceDto;
import com.example.bank.application.port.out.BalanceWriteFallbackReadPort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Optional;

public class MySqlBalanceFallbackReadAdapter implements BalanceWriteFallbackReadPort {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MySqlBalanceFallbackReadAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<AccountBalanceDto> findBalanceFromWriteDb(String accountId) {
        String sql = """
                SELECT id, balance
                FROM accounts
                WHERE id = :accountId
                """;

        var result = jdbcTemplate.query(sql, Map.of("accountId", accountId), (rs, rowNum) ->
                new AccountBalanceDto(
                        rs.getString("id"),
                        rs.getBigDecimal("balance"),
                        "WRITE_DB"
                )
        );

        return result.stream().findFirst();
    }
}
