# ==============================
# STAGE 1: Build with Maven
# ==============================
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy only Maven config files first to leverage Docker cache
COPY pom.xml ./

# Cache Maven dependencies
RUN mvn dependency:go-offline -B

# Copy full source code
COPY src ./src

# Build Spring Boot jar
RUN mvn clean package -Pprod -DskipTests

# ==============================
# STAGE 2: Run JAR
# ==============================
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY configs ./configs/

# Install wget for health check
RUN apt-get update && apt-get install -y --no-install-recommends wget && rm -rf /var/lib/apt/lists/*

# Copy Spring Boot JAR from the build stage
COPY --from=build /app/target/config-server-*.jar app.jar

# Expose port 8888 for Spring Boot
EXPOSE 8888

# Start Spring Boot app
CMD ["java", "-jar", "app.jar"]
