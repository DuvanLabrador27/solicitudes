FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY build/libs/solicitudes-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/solicitudes-0.0.1-SNAPSHOT.jar"]