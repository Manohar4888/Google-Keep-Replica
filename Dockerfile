# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-11 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true

# Stage 2: Create runtime image
FROM maven:3.8.4-openjdk-11 AS runtime
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
