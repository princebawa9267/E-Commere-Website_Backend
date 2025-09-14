# Use OpenJDK image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy JAR file from target folder
COPY target/*.jar app.jar

# Expose the port Spring Boot runs on (default 8080)
EXPOSE 5454

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
