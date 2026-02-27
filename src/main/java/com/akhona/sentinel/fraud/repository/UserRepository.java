package com.akhona.sentinel.fraud.repository;

import com.akhona.sentinel.fraud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}