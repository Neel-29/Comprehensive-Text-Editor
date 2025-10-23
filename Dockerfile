# Multi-stage Dockerfile: build WAR with Maven, run in Tomcat
# Use a maven image that includes JDK 17 (adjust tag if your runner cannot pull it)
FROM maven:3.9.4-openjdk-17-slim AS builder
WORKDIR /workspace

# copy only what is necessary for a reproducible build
COPY pom.xml .
COPY src ./src

# build the WAR (skip tests for faster builds; remove -DskipTests to run tests)
RUN mvn -B clean package -DskipTests

# Final image: Tomcat
FROM tomcat:9.0
# remove default webapps and copy built WAR as ROOT.war
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /workspace/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]