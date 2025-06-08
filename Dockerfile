FROM openjdk:21-jdk

COPY target/devicesapi-0.0.1-SNAPSHOT.jar /app/devicesapi.jar

CMD ["java", "-jar", "/app/devicesapi.jar"]