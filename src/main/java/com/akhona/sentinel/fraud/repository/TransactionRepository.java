package com.akhona.sentinel.fraud.repository;

import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
