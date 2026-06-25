
# ==========================
# Stage 1: Build
# ==========================
FROM gradle:8.14-jdk21 AS build

WORKDIR /app

# Copy gradle wrapper and config first for dependency cache
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Download dependencies
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build application
RUN gradle bootJar --no-daemon -x test


# ==========================
# Stage 2: Runtime
# ==========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
