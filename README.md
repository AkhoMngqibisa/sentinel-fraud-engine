# Sentinel Fraud Engine

Production-grade fraud detection microservice built with:

- Java 21
- Spring Boot 3
- MySQL
- Redis
- JWT Security
- Prometheus Metrics
---

##  Overview

Sentinel Fraud Engine is a scalable, rule-based fraud detection system designed for financial transaction monitoring.

It evaluates transactions against configurable rules including:
- High Amount Detection
- Velocity Rule (Redis-based)
- Geo Location Mismatch
- Blacklist Validation
- Device Fingerprint Detection
- Night-Time Risk
- Rapid Amount Spike Detection

The architecture follows:

- Strategy Pattern (Open/Closed Principle)
- Clean Separation of Concerns
- Event-Driven Ready

---

## Architecture

### High-Level Flow

````
Client → REST API → RuleEngine → FraudRules
↓
Redis / MySQL
↓
Fraud Decision

````

---

## Technology Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Database | MySQL |
| Cache | Redis |
| Security | JWT |
| Metrics | Prometheus |
| Containerization | Docker |

---

## 📁 Project Structure

````
sentinel-fraud-engine
│
├── src/main/java/com/akhona/sentinel/fraud
│ ├── config
│ │ ├── SecurityConfig.java
│ │ └── KafkaConfig.java
│ │
│ ├── controller
│ │ └── TransactionController.java
│ │
│ ├── engine
│ │ └── RuleEngine.java
│ │
│ ├── model
│ │ ├── Transaction.java
│ │ ├── FraudResult.java
│ │ └── FraudDecisiom.java
│ │
│ ├── repository
│ │ ├── UserRepository.java
│ │ └── TransactionRepository.java
│ │
│ ├── rule
│ │ ├── FraudRule.java
│ │ ├── HighAmountRule.java
│ │ ├── VelocityRule.java
│ │ ├── GeoLocationMismatchRule.java
│ │ ├── BlacklistRule.java
│ │ ├── DeviceFingerprintRule.java
│ │ ├── NightTimeTransactionRule.java
│ │ └── RapidAmountIncreaseRule.java
│ │
│ ├── service
│ │ └── FrauEngineService.java
│ │
│ └── SentinelFraudEngineApplication.java
│
├── src/test/java
│ └── FraudIntegrationTest.java
│
├── pom.xml
├── application.properties
└── README.md

````

---
#### 👤 Author
##### Akhona Mngqibisa
##### Software Engineer
##### Cape Town, South Africa