# Args
ARG BASE_IMAGE_JDK=eclipse-temurin:21-jdk
ARG BASE_IMAGE_JRE=eclipse-temurin:21-jre

# Stage 1: Build Stage
FROM $BASE_IMAGE_JDK AS build
WORKDIR /app
COPY . /app
RUN ./gradlew clean build -x test

# Stage 2: Set Up Runtime Stage
FROM $BASE_IMAGE_JRE
WORKDIR /app
EXPOSE 8080
EXPOSE 8081
COPY --from=build /app/build/libs/*.jar /app/service.jar

# Stage 3: Run
CMD ["java", "-Duser.timezone=UTC", "-Dspring.profiles.active=dev", "-XX:+UseZGC", "-XX:ParallelGCThreads=4", "-jar", "/app/service.jar"]