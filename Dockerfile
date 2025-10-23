# Multi-stage Dockerfile: build WAR with Maven, run in Tomcat

# STAGE 1: Build
# Use a valid, standard image for Maven and JDK 17
FROM maven:3-eclipse-temurin-17-slim AS builder
WORKDIR /workspace

# Copy POM and source code
COPY pom.xml .
COPY src ./src

# Build the WAR
RUN mvn -B clean package -DskipTests

# STAGE 2: Run
# Final image: Tomcat
FROM tomcat:9.0
# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy the built WAR from the 'builder' stage
COPY --from=builder /workspace/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]