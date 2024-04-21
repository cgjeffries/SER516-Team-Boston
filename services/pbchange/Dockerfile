FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,target=/root/.m2 mvn -pl services/pbchange -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/pbchange/target/pbchange.jar /opt/app/app.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]