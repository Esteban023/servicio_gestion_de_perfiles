FROM eclipse-temurin:24-jre-noble
ARG JAR_FILE=target/profile_service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
COPY ${JAR_FILE} profile_service.jar
ENTRYPOINT ["java", "-jar", "profile_service.jar"]