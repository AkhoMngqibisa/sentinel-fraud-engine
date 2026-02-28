# Sentinel Fraud Engine

A rule-based, real-time fraud detection engine built with Spring Boot, Kafka, Redis, and MySQL.  
Designed to evaluate financial transactions, produce risk decisions, and integrate with event streams.

It evaluates transactions against configurable rules including:

- High Amount Detection
- Velocity Rule (Redis-based)
- Geo Location Mismatch
- Blacklist Validation

##  Features
- REST API for fraud evaluation
- Rule Engine for configurable fraud logic
- Kafka producer/consumer architecture
- Redis support for caching/fast state
- MySQL persistence
- OpenAPI/Swagger documentation
- Dockerized for local and production use

## Architecture Overview

The architecture follows:

- Strategy Pattern (Open/Closed Principle)
- Clean Separation of Concerns
- Event-Driven Ready

### High-Level Flow

````
Client → REST Controller → Service Layer → Rule Evaluation →  
DB Persistence (MySQL) + Redis cache + Kafka Messaging
````

````

Client → REST API → RuleEngine → FraudRules
↓
Redis / MySQL
↓
Fraud Decision

````

- Java 21
- Spring Boot 3
- MySQL 8
- Redis 7
- JWT Security
- Prometheus Metrics
- Kafka (with Zookeeper)
- Swagger/OpenAPI
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
## Getting Started

### Prerequisites
Install the following tools (local development):

✔ Java 21+  
✔ Maven  
✔ Docker & Docker Compose

---

## Run Locally (Development)

1. Clone the repository
```bash
git clone https://github.com/AkhoMngqibisa/sentinel-fraud-engine.git
cd sentinel-fraud-engine

````
2. Update application.properties with local Redis/MySQL connections

3. Run:
````
mvn clean install
mvn spring-boot:run
````

4. Swagger UI:
```
http://localhost:8080/swagger-ui.html
```
---
## Run with Docker (Recommended)
Build and start all services:

````
docker compose up --build
````

All services start with proper order and health checks.

Swagger UI will be available at:

```
http://localhost:8080/swagger-ui.html

```
Use:
```
Username: admin
Password: admin

```
---
## API Usage

Evaluate Transaction
```
POST /api/v1/transactions

```

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/transactions' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "USR100234",
  "amount": 15000,
  "currency": "ZAR",
  "merchant": "Amazon SA",
  "location": "Cape Town, ZA",
  "merchantId": "MERCH99821",
  "accountId": "ACC12345",
  "deviceId": "DEVICE-ABC-9988"
}'
```

Respone

```
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "fraudScore": 82,
  "flagged": "HIGH",
  "triggeredRules": "HighAmountRule, GeoLocationRule",
  "createdAt": "2026-03-01T22:08:15.109Z"
}

```
----


## Security

- Swagger UI is automatically available.
- Use it to explore request/response models with descriptions.
- Make sure Basic Auth is authorized (Admin credentials).

````
Username: admin
Password: admin
````

These can be overridden in:
```
application.properties
```

## Configuration
| Property                         | Description         |
| -------------------------------- | ------------------- |
| `spring.datasource.url`          | JDBC URL for MySQL  |
| `spring.data.redis.host`         | Redis hostname      |
| `spring.kafka.bootstrap-servers` | Kafka broker list   |
| `spring.security.user.name`      | Basic Auth user     |
| `spring.security.user.password`  | Basic Auth password |



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
- Add Flyway for database migrations
- Add CI/CD pipeline (GitHub Actions)
- Add integration tests using Testcontainers 
- Add JWT authentication instead of Basic Auth
- Add more rules like 
  - Night-Time Risk
  - Rapid Amount Spike Detection

----------------------
#### 👤 Author

##### Akhona Mngqibisa

##### Software Engineer

##### Cape Town, South Africa