FROM openjdk:17
EXPOSE 8082
ADD target/tpFoyer-0.0.1.jar /tpFoyer-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/tpFoyer-0.0.1.jar"]
