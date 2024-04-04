FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /opt/app
COPY . /opt/app/
RUN --mount=type=cache,id=s/1777c0b4-8ef8-4f02-bcf8-532e94d17f20-/root/.m2,target=/root/.m2 ./mvnw -pl services/router -am -DskipTests clean install

FROM eclipse-temurin:17-jre-jammy
ARG PORT
ENV PORT=${PORT}
COPY --from=build /opt/app/services/router/target/router.jar /opt/app/app.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]