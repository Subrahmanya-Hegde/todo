FROM openjdk:17-jdk-slim

WORKDIR /app

COPY application/build/libs/application-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]