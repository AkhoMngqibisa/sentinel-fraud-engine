# Sentinel Fraud Engine

Production-grade fraud detection microservice built with:

- Java 21
- Spring Boot 3
- MySQL
- Redis
- JWT Security
- Prometheus Metrics

---

## Overview

Sentinel Fraud Engine is a scalable, rule-based fraud detection system designed for financial transaction monitoring.

It evaluates transactions against configurable rules including:

- High Amount Detection
- Velocity Rule (Redis-based)
- Geo Location Mismatch
- Blacklist Validation
- Device Fingerprint Detection


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

| Layer            | Technology    |
|------------------|---------------|
| Language         | Java 21       |
| Framework        | Spring Boot 3 |
| Database         | MySQL         |
| Cache            | Redis         |
| Security         | JWT           |
| Metrics          | Prometheus    |
| Containerization | Docker        |

---

## Project Structure

````
sentinel-fraud-engine
│
├── src/main/java/com/akhona/sentinel/fraud
│ ├── config
│ │ ├── CacheConfig.java
│ │ ├── HightAmountRuleProperties.java
│ │ ├── KafkaConfig.java
│ │ ├── SecurityConfig.java
│ │ └── VelocityRuleProperties.java
│ │
│ ├── controller
│ │ └── TransactionController.java
│ │
│ ├── dto
│ │ ├── TransactionRequest.java
│ │ └── FraudResponse.java
│ │
│ ├── exception
│ │ ├── BusinessException.java
│ │ └── GlobalExceptionHandle.java
│ │
│ ├── messaging
│ │ ├── TransactionConsumer.java
│ │ └── TransactionProducer.java
│ │
│ ├── model
│ │ ├── BlacklistEntry.java
│ │ ├── FraudDecision.java
│ │ ├── FraudResult.java
│ │ ├── Transaction.java
│ │ └── User.java
│ │
│ ├── repository
│ │ ├── BlacklistRepository.java
│ │ ├── FraudDecisionRepository.java
│ │ ├── TransactionRepository.java
│ │ └── UserRepository.java
│ │
│ ├── rule
│ │ ├── FraudRule.java
│ │ ├── HighAmountRule.java
│ │ ├── VelocityRule.java
│ │ ├── GeoLocationMismatchRule.java
│ │ ├── BlacklistRule.java
│ │ └── DeviceFingerprintRule.java
│ │
│ ├── service
│ │ └── FrauEngineService.java
│ │
│ ├── security
│ │ ├── filter
│ │ │  └── FrauEngineService.java
│ │ └── JwtService.java
│ │
│ └── SentinelFraudEngineApplication.java
│
├── src/test/java
│ └── FraudEngineTest.java
│
├── pom.xml
├── application.properties
└── README.md

````

## Configuration

### application.properties

```properties
# MySQL
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/fraudengine?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=fraudengineuser
spring.datasource.password=Fraudenginep@ss
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
# JWT
jwt.secret=change-me
jwt.expiration=3600000
# Fraud Rules
fraud.rules.high-amount.threshold=10000
fraud.rules.high-amount.currency=ZAR
fraud.rules.velocity.window-seconds=60
fraud.rules.velocity.max-transactions=5
```

---

## Security

All endpoints require JWT authentication.

````
Authorization: Bearer <token>
````

## API

Evaluate Transaction

POST /api/v1/transactions

```
{
  "id": "tx-123",
  "userId": "user-1",
  "amount": 15000,
  "currency": "ZAR",
  "merchantId": "m-100",
  "accountId": "acc-1",
  "country": "US",
  "deviceId": "device-abc",
  "timestamp": "2026-02-22T02:00:00"
}
```

Respone

```
{
  "flagged": true,
  "ruleCode": "HIGH_AMOUNT",
  "message": "Transaction exceeds allowed threshold"
}

```

## Testing

Run unit tests:

```
mvn test
```

## Observability

Exposes

```
/actuator/prometheus
```

## Docker

Build image:

```
docker build -t sentinel-fraud-engine .
```

Run with docker-compose:
```
docker-compose up
```

## Future Enhancements
- Weighted risk scoring
- Kafka event streaming
- Rule management UI
- Dynamic rule toggling
- Machine learning integration
- Add more rules like 
  - Night-Time Risk
  - Rapid Amount Spike Detection

----------------------
#### 👤 Author

##### Akhona Mngqibisa

##### Software Engineer

##### Cape Town, South Africa