FROM adoptopenjdk/openjdk11
COPY build/libs/work4me-oauth-server-*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]