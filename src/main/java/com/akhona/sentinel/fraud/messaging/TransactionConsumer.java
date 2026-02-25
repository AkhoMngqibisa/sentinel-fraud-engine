package com.akhona.sentinel.fraud.messaging;

import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionConsumer {

    @KafkaListener(topics = "transaction-events", groupId = "fraud-group")
    public void listen(Transaction transaction) {

        System.out.println("Received transaction: " + transaction.getId());

        if (transaction.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            System.out.println("Potential Fraud Detected!");
        }
    }
}