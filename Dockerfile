FROM gradle:jdk10 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:10-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/src/build/libs/axon-jgroups-demo-1.0-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT java -Djava.net.preferIPv4Stack=true -jar axon-jgroups-demo-1.0-SNAPSHOT.jar
