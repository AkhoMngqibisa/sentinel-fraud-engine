package com.akhona.sentinel.fraud.messaging;

import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Transaction transaction) {
        kafkaTemplate.send("transaction-events", transaction);
    }
}