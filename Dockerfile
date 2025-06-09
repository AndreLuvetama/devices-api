FROM openjdk:21-jdk

COPY target/devicesapi-0.0.1-SNAPSHOT.jar /app/devicesapi.jar
EXPOSE 8089

CMD ["java", "-jar", "/app/devicesapi.jar"]