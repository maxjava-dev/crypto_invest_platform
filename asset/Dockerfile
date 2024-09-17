FROM openjdk
WORKDIR /app

COPY target/asset-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]