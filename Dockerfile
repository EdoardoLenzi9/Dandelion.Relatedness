FROM openjdk:latest
COPY . /usr/src/dandelion
WORKDIR /usr/src/dandelion
CMD ["java", "-javaagent:./Dandelion.Relatedness/lib/jmx_prometheus_javaagent-0.3.1.jar=8080:./Dandelion.Relatedness/lib/configs.yaml", "-jar", "./Dandelion.Relatedness/dist/Dandelion.jar"]