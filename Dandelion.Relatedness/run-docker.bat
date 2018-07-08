docker build -t dandelion-container .
docker stop dandelion-volume
docker rm dandelion-volume
docker run -it -p 8080:8080 -v C:/Code/Dandelion.Relatedness/Dandelion.Relatedness.:/usr/src/dandelion --name dandelion-volume dandelion-container
