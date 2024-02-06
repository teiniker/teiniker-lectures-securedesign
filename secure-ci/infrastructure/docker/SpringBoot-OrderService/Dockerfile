FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /opt/service
COPY target/SpringBoot-OrderService-1.0.jar /opt/service
CMD ["java", "-jar", "/opt/service/SpringBoot-OrderService-1.0.jar"]
