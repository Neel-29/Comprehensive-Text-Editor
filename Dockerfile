FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

FROM maven:3.8.8-openjdk-17 AS builder
WORKDIR /workspace

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn -B clean package -DskipTests

FROM tomcat:9.0
# clean default webapps and copy built WAR as ROOT.war
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /workspace/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]