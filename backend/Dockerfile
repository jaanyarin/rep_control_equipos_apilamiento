FROM maven:3.9.15-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY backend/pom.xml ./
COPY backend/src ./src

RUN mvn -B -DskipTests=true -Dquarkus.package.type=uber-jar clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN groupadd -r repcontrol && useradd -r -g repcontrol -d /app -s /sbin/nologin repcontrol

COPY --from=builder /workspace/target/quarkus-app ./quarkus-app
COPY --from=builder /workspace/target/*.jar ./app.jar

RUN mkdir -p /app/uploads/evidences && chown -R repcontrol:repcontrol /app

USER repcontrol

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

ENV EVIDENCE_UPLOAD_PATH=/app/uploads/evidences

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
