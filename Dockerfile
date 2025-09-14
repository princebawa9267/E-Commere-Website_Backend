# ======================
# Stage 1: Build the JAR
# ======================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (cache step)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the project (skip tests for faster builds)
RUN mvn clean package -DskipTests

# ======================
# Stage 2: Run the JAR
# ======================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose your app’s port (matches application.properties)
EXPOSE 5454

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
