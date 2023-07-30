FROM maven:3-eclipse-temurin-17-alpine as builder
WORKDIR /app
COPY . .
RUN ["mvn", "clean", "package"]

FROM eclipse-temurin:17-jre-alpine as runner
WORKDIR /app
COPY --from=builder /app/target/fast-order-backend.jar .
CMD ["java", "-jar", "fast-order-backend.jar"]