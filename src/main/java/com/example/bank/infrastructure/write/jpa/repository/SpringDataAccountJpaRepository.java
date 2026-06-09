package com.example.bank.infrastructure.write.jpa.repository;

import com.example.bank.infrastructure.write.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountJpaRepository extends JpaRepository<AccountJpaEntity, String> {
}
