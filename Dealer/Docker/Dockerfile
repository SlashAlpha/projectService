FROM openjdk:17 as builder
WORKDIR application
#COPY maven/${project.build.finalName}.jar ${project.build.finalName}.jar
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} dealer.jar
#ADD maven/${project.build.finalName}.jar usr/local/${project.build.finalName}.jar
RUN java -Djarmode=layertools -jar dealer.jar extract

FROM openjdk:17
LABEL PROJECT_NAME=dealer
      PROJECT=dealer

EXPOSE 8080



WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]