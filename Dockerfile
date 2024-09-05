FROM maven:3.8.7-openjdk-22
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/hoversprite.jar"]

