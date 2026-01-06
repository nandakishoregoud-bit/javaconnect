#  Use Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies first (faster rebuilds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

#  Use a lightweight JDK image for running
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (default Spring Boot port)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
