FROM openjdk:17-oracle
COPY target/*.jar coding-dojo-spring-boot.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","coding-dojo-spring-boot.jar"]