FROM adoptopenjdk:11-jdk-openj9 as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw

RUN chmod +x ./mvnw

RUN ./mvnw install -DskipTests -Pprod
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM adoptopenjdk:11-jdk-openj9
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.apsi.repo.RepoApplication"]