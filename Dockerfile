FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/crystals-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
