FROM openjdk:22-jdk-buster
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/hoversprite.jar"]

