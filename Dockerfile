FROM java:openjdk-8-jre-alpine

ADD build/libs/skeleton-api-0.0.1.jar skeleton-api-0.0.1.jar

RUN sh -c 'touch /skeleton-api-0.0.1.jar'

EXPOSE 8080

ENTRYPOINT ["java","-jar","/skeleton-api-0.0.1.jar"]