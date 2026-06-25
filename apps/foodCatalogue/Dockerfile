# ==========================
# Stage 1: Build
# ==========================
FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for dependency cache
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests


# ==========================
# Stage 2: Runtime
# ==========================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]