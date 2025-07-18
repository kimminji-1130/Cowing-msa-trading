FROM gradle:8.14.2-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

COPY src ./src
RUN ./gradlew build -x test --no-daemon

FROM amazoncorretto:21.0.7-alpine3.19

ARG APP_USER_ID=1001
ARG APP_GROUP_ID=1001
RUN addgroup --gid ${APP_GROUP_ID} javauser && \
    adduser --uid ${APP_USER_ID} --ingroup javauser --no-create-home --disabled-password javauser

WORKDIR /app

COPY --from=build --chown=javauser:javauser /app/build/libs/*.jar /app.jar

RUN chmod 500 /app.jar

USER javauser

EXPOSE 8082

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]