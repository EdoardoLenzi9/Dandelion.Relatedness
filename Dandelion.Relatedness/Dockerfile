FROM openjdk:latest
COPY . /usr/src/dandelion
WORKDIR /usr/src/dandelion
CMD ["java", "-Xmx6144m", "-javaagent:./lib/jmx_prometheus_javaagent-0.3.1.jar=8080:./lib/configs.yaml", "-jar", "/usr/src/dandelion/dist/Dandelion.jar"]