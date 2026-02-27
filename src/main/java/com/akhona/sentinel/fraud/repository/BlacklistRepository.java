package com.akhona.sentinel.fraud.repository;

import com.akhona.sentinel.fraud.model.BlacklistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistEntry, Long> {

    boolean existsByTypeAndValue(String type, String value);
}
