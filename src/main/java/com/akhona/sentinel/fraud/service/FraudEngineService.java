package com.akhona.sentinel.fraud.service;

import com.akhona.sentinel.fraud.dto.FraudResponse;
import com.akhona.sentinel.fraud.dto.TransactionRequest;
import com.akhona.sentinel.fraud.exception.BusinessException;
import com.akhona.sentinel.fraud.messaging.TransactionProducer;
import com.akhona.sentinel.fraud.model.FraudDecision;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.FraudDecisionRepository;
import com.akhona.sentinel.fraud.repository.TransactionRepository;
import com.akhona.sentinel.fraud.rule.FraudRule;
import com.akhona.sentinel.fraud.rule.RuleRegistry;
import com.akhona.sentinel.fraud.rule.RuleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FraudEngineService {

    private final RuleRegistry ruleRegistry;
    private final FraudDecisionRepository decisionRepository;
    private final TransactionProducer transactionProducer;
    private final TransactionRepository transactionRepository;
    private static final Logger log = LoggerFactory.getLogger(FraudEngineService.class);

    public FraudEngineService(RuleRegistry ruleRegistry, FraudDecisionRepository decisionRepository, TransactionProducer transactionProducer, TransactionRepository transactionRepository) {
        this.ruleRegistry = ruleRegistry;
        this.decisionRepository = decisionRepository;
        this.transactionProducer = transactionProducer;
        this.transactionRepository = transactionRepository;
    }

    public FraudResponse assess(TransactionRequest request) {

        validateRequest(request);

        Transaction transaction = mapToEntity(request);

        Transaction savedTransaction = saveTransaction(transaction);

        FraudEvaluation evaluation = evaluateRules(savedTransaction);

        persistDecision(savedTransaction, evaluation);

        publishEvent(savedTransaction);

        return buildResponse(evaluation);
    }

    private void validateRequest(TransactionRequest request) {
        if (request.userId().isEmpty()) {
            throw new BusinessException("Invalid transaction", List.of("User identity required"));
        }
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Invalid transaction", List.of("Amount must be greater than 0"));
        }

        if (request.currency().isEmpty()) {
            throw new BusinessException("Invalid transaction", List.of("Transaction currency required"));
        }

        if (request.location().isEmpty()) {
            throw new BusinessException("Invalid transaction", List.of("User location required"));
        }
        if (request.accountId().isEmpty()) {
            throw new BusinessException("Invalid transaction", List.of("User account required"));
        }

        if (request.deviceId().isEmpty()) {
            throw new BusinessException("Invalid transaction", List.of("User device required"));
        }
    }

    private Transaction mapToEntity(TransactionRequest request) {

        return Transaction.builder()
                .userId(request.userId())
                .amount(request.amount())
                .currency(request.currency())
                .merchant(request.merchant())
                .location(request.location())
                .accountId(request.accountId())
                .deviceId(request.deviceId())
                .timestamp(LocalDateTime.now())
                .build();
    }

    private Transaction saveTransaction(Transaction transaction) {

        log.info("Saving transaction for account {}", transaction.getAccountId());
        return transactionRepository.save(transaction);
    }

    private FraudEvaluation evaluateRules(Transaction transaction) {

        int totalScore = 0;
        List<String> triggeredRules = new ArrayList<>();

        for (FraudRule rule : ruleRegistry.getRules()) {

            RuleResult result = rule.check(transaction);

            if (result.isTriggered()) {
                totalScore += result.getScore();
                triggeredRules.add(rule.getRuleName());
            }
        }

        boolean flagged = totalScore >= 70;

        return new FraudEvaluation(transaction.getId(),totalScore, flagged, triggeredRules, transaction.getTimestamp());
    }

    private record FraudEvaluation(
            UUID transactionId,
            int score,
            boolean flagged,
            List<String> triggeredRules,
            LocalDateTime timestamp
    ) {
    }

    private void persistDecision(Transaction transaction, FraudEvaluation evaluation) {
        FraudDecision decision = FraudDecision.builder()
                .transactionId(transaction.getId())
                .fraudScore(evaluation.score())
                .flagged(evaluation.flagged())
                .triggeredRules(evaluation.triggeredRules())
                .createdAt(LocalDateTime.now())
                .build();

        decisionRepository.save(decision);
    }

    private void publishEvent(Transaction transaction) {
        transactionProducer.send(transaction);
    }

    private FraudResponse buildResponse(FraudEvaluation evaluation) {

        String riskLevel = evaluation.flagged() ? "HIGH" : "LOW";

        String reason = evaluation.triggeredRules().isEmpty()
                ? "No suspicious patterns detected"
                : String.join(", ", evaluation.triggeredRules());

        return new FraudResponse(
                evaluation.transactionId,
                evaluation.score,
                riskLevel,
                reason,
                evaluation.timestamp
        );
    }


//    public FraudDecision assess(TransactionRequest transaction) {
//        int totalScore = 0;
//        List<String> triggered = new ArrayList<>();
//
//        if (transaction.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
//            throw new BusinessException("Invalid transaction", List.of("Amount must be greater than 0"));
//        }
//
//        request.setTimestamp(LocalDateTime.now());
//        log.info("Assessing transaction {}", transaction.getId());
//
//        // Save transaction
//        Transaction result = repository.save(transaction);
//
//
//        // 1. Evaluate
//        for (FraudRule rule : ruleRegistry.getRules()) {
//            // Get Transaction rule results
//            RuleResult result = rule.check(transaction);
//
//            if (result.isTriggered()) {
//                totalScore += result.getScore();
//                triggered.add(rule.getRuleName());
//            }
//        }
//
//        // 2. Log evaluation
//        boolean flagged = totalScore >= 70;
//        FraudDecision fraudDecision = FraudDecision.builder()
//                .transactionId(transaction.getId())
//                .fraudScore(totalScore)
//                .flagged(flagged)
//                .triggeredRules(triggered)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // 3. Publish event
//        transactionProducer.send(transaction);
//        log.info("Fraud result: score={}, flagged={}, rules={}", totalScore, flagged, triggered);
//        return decisionRepository.save(fraudDecision);
//    }
}
