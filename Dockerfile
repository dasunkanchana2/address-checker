FROM openjdk:21-slim
EXPOSE 8089:8089
ADD /target/address-checker-1.0.0-SNAPSHOT.jar address-checker-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","address-checker-1.0.0-SNAPSHOT.jar"]