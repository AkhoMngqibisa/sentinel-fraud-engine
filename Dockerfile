# Use OpenJSK 17
FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Akhona"

WORKDIR /app
ARG JAR_FILE=target/sentinel-fraud-engine.jar

# Copy the jar into containers
COPY ${JAR_FILE} app.jar

COPY wait-for-it.sh /wait-for-it.sh
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]

# Expose Spring boot default port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]