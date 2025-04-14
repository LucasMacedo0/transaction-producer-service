FROM openjdk:17-jdk

WORKDIR /app

COPY target/transaction-producer-service.jar /app/transaction-producer-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transaction-producer-service.jar"]
