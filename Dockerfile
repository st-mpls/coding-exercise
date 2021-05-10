FROM  gradle:jdk14 as builder

WORKDIR /app

COPY ./src/main ./src/main
COPY ./gradle ./gradle
COPY ./gradlew .
COPY ./gradlew.bat .
COPY ./build.gradle.kts .
COPY ./settings.gradle.kts .
COPY ./application.properties ./

RUN gradle --no-daemon assemble

FROM adoptopenjdk/openjdk14:alpine-slim
EXPOSE 8080
WORKDIR /app
COPY --from=builder /app/build/resources .
COPY --from=builder /app/application.properties .
COPY --from=builder /app/build/libs .
RUN jar -xf exercise-0.0.1-SNAPSHOT.jar
CMD ["java", "-cp", "BOOT-INF/classes:BOOT-INF/lib/*", "com.st.exercise.ExerciseApplicationKt"]
