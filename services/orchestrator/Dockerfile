FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/orchestrator/target/orchestrator.jar /opt/app/app.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]