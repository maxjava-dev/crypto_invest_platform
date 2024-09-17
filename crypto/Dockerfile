FROM openjdk
WORKDIR /app

COPY target/crypto-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]