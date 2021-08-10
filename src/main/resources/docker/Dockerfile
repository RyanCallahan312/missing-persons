FROM openjdk:16-jdk-buster as builder
COPY . .
RUN ./gradlew clean build

FROM openjdk:16-alpine as runtime
COPY --from=builder /build/libs/missing-persons-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]