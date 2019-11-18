FROM adoptopenjdk/openjdk11:jre-11.0.5_10-alpine

COPY ./build/libs/winterboots-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]