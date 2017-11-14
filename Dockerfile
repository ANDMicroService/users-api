FROM java:openjdk-8-jre-alpine

ADD build/libs/users-api-0.0.1.jar users-api-0.0.1.jar

RUN sh -c 'touch /users-api-0.0.1.jar'

EXPOSE 8080

ENTRYPOINT ["java","-jar","/users-api-0.0.1.jar"]