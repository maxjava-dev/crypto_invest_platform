FROM openjdk
WORKDIR /app

COPY target/backtofront-2.7.0.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]