FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY target/crystals-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar", "-Dspring.profiles.active=prod"]