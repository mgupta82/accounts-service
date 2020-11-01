FROM openjdk:8-jdk-alpine
EXPOSE 8090
VOLUME /tmp
ARG JAR_FILE=build/libs/accounts-service-*.jar
COPY ${JAR_FILE} accounts-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/accounts-service.jar"]