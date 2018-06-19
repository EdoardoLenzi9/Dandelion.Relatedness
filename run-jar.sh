java \
-Dfile.encoding=UTF-8 \
-javaagent:/home/eddy/Code/Dandelion.Relatedness/Dandelion.Relatedness/lib/ObjectSizeFetcher.jar \
-javaagent:./Dandelion.Relatedness/lib/jmx_prometheus_javaagent-0.3.1.jar=8080:./Dandelion.Relatedness/lib/configs.yaml \
-jar ./Dandelion.Relatedness/dist/Dandelion.jar
