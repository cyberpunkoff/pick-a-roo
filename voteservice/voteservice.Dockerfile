FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/voteservice.jar /app/voteservice.jar
EXPOSE 8090
CMD ["java", "-jar", "scrapper.jar"]
