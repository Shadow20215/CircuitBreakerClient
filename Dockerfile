FROM openjdk:17
ADD build/libs/CircuitBreakerClient-0.0.1-SNAPSHOT.jar CircuitBreakerClient-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "CircuitBreakerClient-0.0.1-SNAPSHOT.jar"]