FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Akhona"

WORKDIR /app

COPY target/fraud-detection.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]