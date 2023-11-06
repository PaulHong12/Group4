FROM openjdk:17-alpine

ADD build/libs/simplesns-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "/simplesns-0.0.1-SNAPSHOT.jar"]