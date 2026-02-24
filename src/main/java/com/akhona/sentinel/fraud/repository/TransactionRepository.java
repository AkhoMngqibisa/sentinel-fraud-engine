package com.akhona.sentinel.fraud.repository;

import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.userId = :userId
            AND t.timestamp >= :date
            """)
    List<Transaction> findRecentTransactions(String userId, LocalDateTime date);
}
