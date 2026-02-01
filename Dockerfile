# --- Stage 1: The Builder ---
# This part downloads Maven and builds your project
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: The Runner ---
# This part takes only the finished JAR from Stage 1 and runs it
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the JAR from the 'builder' stage above
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]