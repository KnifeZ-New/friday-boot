FROM openjdk:17-jdk-alpine
COPY fridayboot-api/target/fridayboot-host.jar /app.jar
EXPOSE 5051
ENTRYPOINT ["java", "-jar", "/app.jar"]