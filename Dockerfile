FROM gradle:8.5.0-jdk17 AS builder

WORKDIR /blog
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17
COPY --from=builder /blog/build/libs/ /app

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar" ]