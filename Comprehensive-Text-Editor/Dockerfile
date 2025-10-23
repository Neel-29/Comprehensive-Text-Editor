FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Copy the WAR file to the webapps directory
RUN mkdir -p /usr/local/tomcat/webapps && \
    cp target/webapp.war /usr/local/tomcat/webapps/

# Expose the port the app runs on
EXPOSE 8080

# Start the Tomcat server
CMD ["catalina.sh", "run"]