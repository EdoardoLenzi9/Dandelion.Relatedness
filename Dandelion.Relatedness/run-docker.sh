ant clean
ant compile
ant build
ant jar
sudo docker stop dandelion-volume
sudo docker rm dandelion-volume
sudo docker run -it -p 8080:8080 -v /home/eddy/Code/Dandelion.Relatedness/Dandelion.Relatedness/.:/usr/src/dandelion --name dandelion-volume dandelion-container
