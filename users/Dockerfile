FROM openjdk
WORKDIR /app

COPY target/users-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]