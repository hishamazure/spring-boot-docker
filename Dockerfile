FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "-Dsun.net.inetaddr.ttl=0", "/spring-boot-docker.jar"]