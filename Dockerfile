# Args
ARG BASE_IMAGE_JDK=eclipse-temurin:21-jdk
ARG BASE_IMAGE_JRE=eclipse-temurin:21-jre

# Stage 1: Dependencies caching
FROM $BASE_IMAGE_JDK AS deps
WORKDIR /app
VOLUME /root/.gradle
COPY gradle gradle
COPY gradlew gradlew
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY gradle.properties gradle.properties
COPY modules modules
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Stage 2: App Build Stage
FROM deps AS build
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Stage 3: Set Up Runtime Stage
FROM $BASE_IMAGE_JRE
WORKDIR /app
EXPOSE 8080
EXPOSE 8081
COPY --from=build /app/build/libs/*.jar /app/service.jar

# Stage 4: Run
CMD ["java", "-Duser.timezone=UTC", "-Dspring.profiles.active=dev", "-XX:+UseZGC", "-XX:ParallelGCThreads=4", "-jar", "/app/service.jar"]