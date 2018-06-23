git pull origin master
gnome-system-monitor &

cd /home/eddy/App/prometheus-2.3.0.linux-amd64/
./prometheus &

firefox http://localhost:3000/login
firefox http://localhost:9090/graph?g0.range_input=1m&g0.expr=jvm_memory_bytes_used{area%3D%22heap%22%2C}&g0.tab=0&g1.range_input=5m&g1.expr=jvm_memory_bytes_used{area%3D%22nonheap%22%2C}&g1.tab=0&g2.range_input=1h&g2.expr=&g2.tab=0
firefox http://localhost:8080/metrics

cd /home/eddy/Code/Dandelion.Relatedness/Dandelion.Relatedness/
sh refresh-docker.sh