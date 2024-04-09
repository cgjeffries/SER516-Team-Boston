FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /opt/app
COPY . /opt/app/

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/pbhealth/target/pbhealth.jar /opt/app/app.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]