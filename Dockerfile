FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} f9-v1.0.jar
ENTRYPOINT ["java", "-jar", "/f9-v1.0.jar"]