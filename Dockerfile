FROM jelastic/maven:3.9.4-openjdk-22.ea-b17

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

ENV JAR_FILE=main/target/main-0.0.1-SNAPSHOT.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/main/target/main-0.0.1-SNAPSHOT.jar"]