# Multi-stage Dockerfile for building and running the Quarkus application
# Builder: use Maven with a JDK 25 base to compile and package
FROM maven:3.9.15-eclipse-temurin-25 AS builder
WORKDIR /workspace
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn -B -DskipTests=true package

# Runtime image: use a slim Temurin JRE 25
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /workspace/target/*-runner.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
