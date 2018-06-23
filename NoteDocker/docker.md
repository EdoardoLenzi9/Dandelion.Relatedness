# Prometheus and Grafana Using Docker
sudo docker pull prom/prometheus
sudo docker pull grafana/grafana
sudo docker images

mkdir /opt/docker/prometheus
touch /opt/docker/prometheus/prometheus.yml
docker run -it -d --name prometheus -p 9090:9090 -v /opt/docker/prometheus:/etc/prometheus prom/prometheus -config.file=/etc/prometheus/prometheus.yml
docker ps
localhost:9090/graph 

global:
    scrate_interval:    15s
    evaluation_interval:    15s
    external_labels:
        monitor: 'codelab-monitor'

    scrape_configs:
        - job_name: 'prometheus'
          scrape_interval: 5s
          static_configs:
            - targets: ['localhost:9090']
    #################
    # Node Exporter #
    #################

    - job_name: 'node_exporter_labs'
      scrape_interval: 5s 
      static_configs:
        - targets: ['172.16.10.164:9100']
          labels:
            group: 'node_exporter'


hadoop

# Base Image
* Java -> openjdk as base image (deprecated)
hub.docker.com/_/openjdk/

* Alpine (meno roba)

* Oracle JDK (licenza)

* zulu-openjdk

## Maven or Gradle
Maven plugin (fabric8)

<groupId>io.frabric8</groupId>
<artifactId>docker-maven-plugin</artifactId>
<version>0.20.1</version>

docker:X, X=stop, build, push

## Docker Benefits
* You can share an environment through any platform because it's cross platform
> Environment contains many containers each container is an instance

Use powershell

docker -v 
docker run hello-world

docker run --help 

interactive, tty
docker run -it ubuntu

docker run -it --name myname ubuntu 

ctrl PQ continua a runnare ma vado fuori dalla shell 

docker ps 

docker attach myname 
or exit

docker rmi hello-world //remove image 

docker images 

docker run -it ubuntu 
docker rm -f name 

## dockerfile
hub.docker.com 

search dockerfile 

save as .Dockerfile 
sudo docker build -t dandelion .
sudo docker build -t dandelion -f ./.Dockerfile 

## run your app 
docker run -it -rm --nae my-running-app my-nodejs-app 

## port mapping 
docker run -it -p 1337:80 --name hi hello-system 
localhost:1337

docker rm -f hi 

## bind fs to linux fs 
docker run -it -p 3000:80 -v //c/users/user/desktop/.:/usr/src/app --name hi hello-system 

## Overview

Istanze di immagini sono chiamate containers  
Containers aren't persistent 
Images are persistent 

Volumes are data layers used by multiple containers 
Networks wraps some containers

Download Docker CE (Comunity Edition)

## download image
Ogni volta che faccio run di un'immagine se non è presente sul pc viene scaricata direttamente da hub.docker 
```[sh]
    sudo docker run -it --name my-java-container openjdk
```

docker rm $(docker ps -a -f status=exited -q)

## Con il Dockerfile creo un'immagine 
FROM ubuntu //parto da un container esistente
CMD echo "hello Jonny"


docker build -t my-ubuntu-image .
docker images 

docker run my-ubuntu-image

FROM ubuntu 
RUN apt-get update && apt-get update && apt-get install -y python

## Search package
sudo docker search grafana 
sudo docker pull grafana/grafana
prom/prometheus 
sudo docker run -it -d --name grafana -p 3000:3000 grafana/grafana
localhost:3000/login


mkdir /opt/docker/prometheus 
touch /opt/docker/prometheus/prometheus.yml
docker images 
docker run -id -d --name prometheus -p 9090:9090 -v /opt/docker/prometheus:/etc/prometheus prom/prometheus -config.file=/etc/prometheus/prometheus.yml
docker ps 


Open source project created by ex Googles now working as SoundCloud. Time time-series database, un db di floating point numbers con time stamps
-> ha anche un query language per pigliare i dati 
-> exporters servono per dashboards 

## node exporter 

provide metrics about Linux os (network, cpu, ...)
./node-exporter 

localhost:9100/metrics 

# Prometheus static configuration
vi prometheus.yml

> job-name: 'node'
> target: ['localhost:9100']


localhost:9090/graph

## Query
network_received_bytes{device="eth0"} //last
network_received_bytes{device="eth0"}[5m] //last 5 minutes

rate(network_received_bytes{device="eth0"}[5m]) //average percentage

# Grafana 
cd grafana/
/bin/grafana--server

localhost:3000
admin
password

DataSource > Type > Prometheus 
New > Graph > Query 

# Alert

# Java
> prometheus library java


# Maven 
POM (Project Object Model) info from single source of docs
* Compile source code
* copy resource 
* compile and run tests
* package project 
* deploy project 
* cleanup

POM contiene info sul project, lo descrive 

-> JDK and JAVA_HOME variable 
-> System > advanced system settings > Environment variables > Java_Home point to jdk 

System var > path > add jdk bin path 

# download maven
.zip

Add new environment variable
M2_HOME -> path 
MAVEN_HOME -> same path

;bin path 

mvn -version 

# first java project with maven 
mvn archetype:generate //crea un project da un template esistente 
fa il download e ti crea quello di default con l'ultima versione  

* group-id //unique in your org for a project (com.codebind reverse notation) 
* artifact-id //il nome del progetto 
* version //1.0-SNAPSHOT -> developer version 

-> quando creo il jar artifactId-extension.version.jar 

-> package name 
group-id.qualcosa


# create jar file
* compile source .java in .class file 
* compile test files 
* run tests 
* create jar 

---------------------------------------------------------
# [Jmx Exporter](https://github.com/prometheus/jmx_exporter)
* Download jar and run 
```[sh]
    java -javaagent:./jmx_prometheus_javaagent-0.3.1.jar=8080:config.yaml -jar yourJar.jar
```


# Java cli
javac HelloWorld.java
java HelloWorld
jar cf <file.jar> <input-file(s)>

java -jar <jar-file-name>.jar

Jar manifest 
https://stackoverflow.com/questions/9689793/cant-execute-jar-file-no-main-manifest-attribute

jar cmvf META-INF/MANIFEST.MF testino.jar HelloWorld.java

## RUN vs CMD

RUN is an image build step, the state of the container after a RUN command will be committed to the docker image. A Dockerfile can have many RUN steps that layer on top of one another to build the image.

CMD is the command the container executes by default when you launch the built image. A Dockerfile can only have one CMD. The CMD can be overridden when starting a container with docker run $image $other_command.

ENTRYPOINT is also closely related to CMD and can modify the way a container starts an image.


## Run jar
FROM openjdk:latest
COPY . /usr/src/danelion
WORKDIR /usr/src/danelion
CMD ["java", "-jar", "/usr/src/myapp/Dandelion.jar"]

> devi essere dentro a /usr/src/ altrimenti non hai i permessi per accedere ai files

## Grafana
wget https://s3-us-west-2.amazonaws.com/grafana-releases/release/grafana_5.1.3_amd64.deb
sudo dpkg -i grafana_5.1.3_amd64.deb