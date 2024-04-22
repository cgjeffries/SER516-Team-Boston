FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,id=s/88eda921-4991-40f8-9038-26e46a62a042-/root/.m2,target=/root/.m2 mvn -pl services/taskinertia -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/taskinertia/target/taskinertia.jar /opt/app/app.jar
EXPOSE $PORT
ENV TZ=America/Phoenix
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]