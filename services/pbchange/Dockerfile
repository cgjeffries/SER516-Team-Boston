FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,id=s/38609da4-55b8-4457-a877-5d605de9bf51-/root/.m2,target=/root/.m2 mvn -pl services/pbchange -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/pbchange/target/pbchange.jar /opt/app/app.jar
EXPOSE $PORT
ENV TZ=America/Phoenix
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]