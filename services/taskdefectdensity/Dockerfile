FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,id=s/9e671b10-869b-47e1-a4e1-59f36eb52e7e-/root/.m2,target=/root/.m2 mvn -pl services/taskdefectdensity -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/taskdefectdensity/target/taskdefectdensity.jar /opt/app/app.jar
EXPOSE $PORT
ENV TZ=America/Phoenix
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]