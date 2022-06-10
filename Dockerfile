#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/api/src
COPY pom.xml /usr/src/app/api
RUN mvn -f /usr/src/app/api/pom.xml clean install -DskipTests

#
# Package stage
#
FROM openjdk:11-slim
WORKDIR /usr/src/app/api
COPY --from=build /usr/src/app/api/target/files-managment-service-1.0.0.jar /usr/src/app/api/fr.esgi.FilesManagmentServiceApplication.jar

ENTRYPOINT ["java","-jar","fr.esgi.FilesManagmentServiceApplication.jar"]