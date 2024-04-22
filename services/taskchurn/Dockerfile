FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,id=s/c5a4764a-f326-459b-b86e-b90af09455c0-/root/.m2,target=/root/.m2 mvn -pl services/taskchurn -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/taskchurn/target/taskchurn.jar /opt/app/app.jar
EXPOSE $PORT
ENV TZ=America/Phoenix
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]