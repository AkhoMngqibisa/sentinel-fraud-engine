package com.akhona.sentinel.fraud.model;

import jakarta.persistence.*;

@Entity
public class BlacklistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // MERCHANT or ACCOUNT
    private String value;
}
