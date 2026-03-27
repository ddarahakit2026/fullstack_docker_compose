FROM openjdk:17-ea-17-slim
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]