FROM openjdk:17-jdk-alpine
COPY target/knightboard-1.0.0.jar knightboard-1.0.0.jar
ENTRYPOINT ["java","-jar","/knightboard-1.0.0.jar"]