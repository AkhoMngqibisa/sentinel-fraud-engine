package com.akhona.sentinel.fraud.repository;

import com.akhona.sentinel.fraud.model.FraudDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface FraudDecisionRepository extends JpaRepository<FraudDecision, UUID> {
    List<FraudDecision> findByFlagged();
}
