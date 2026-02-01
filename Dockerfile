# 1. Use an official Java runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy your built jar file into the container
# IMPORTANT: Replace 'target/*.jar' with your actual jar path if it differs
COPY target/*.jar policy-application.jar

# 4. Expose the port the app runs on
EXPOSE 8080

# 5. Run the jar file
ENTRYPOINT ["java", "-jar", "policy-application.jar"]