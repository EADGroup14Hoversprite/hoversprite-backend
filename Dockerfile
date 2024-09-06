FROM openjdk:22
ARG JAR_FILE=main/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]