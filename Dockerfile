# ── Build Stage ──────────────────────────────────────────────────
# Use the official Maven image with Java 21 (no need to install Maven separately)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy pom.xml first and download dependencies (Docker layer cache)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Runtime Stage ────────────────────────────────────────────────
# Use slim JRE-only image for minimal footprint
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/bfhl-api-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
