# ===== Stage 1: Build Application =====
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies first for build cache optimization
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build application
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Stage 2: Run Application =====
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (change if your app uses a different one)
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
