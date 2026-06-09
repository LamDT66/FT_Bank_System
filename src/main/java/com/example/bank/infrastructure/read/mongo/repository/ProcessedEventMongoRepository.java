package com.example.bank.infrastructure.read.mongo.repository;

import com.example.bank.infrastructure.read.mongo.document.ProcessedEventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedEventMongoRepository extends MongoRepository<ProcessedEventDocument, String> {
}

