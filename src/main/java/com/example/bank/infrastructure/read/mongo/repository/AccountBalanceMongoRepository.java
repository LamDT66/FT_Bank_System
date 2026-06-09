package com.example.bank.infrastructure.read.mongo.repository;

import com.example.bank.infrastructure.read.mongo.document.AccountBalanceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountBalanceMongoRepository extends MongoRepository<AccountBalanceDocument, String> {
}

