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