FROM openjdk:11
ADD target/cloud-gateway-service.jar cloud-gateway-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/cloud-gateway-service.jar"]