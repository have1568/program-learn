
# docker 私服搭建

docker run -d  -p 5000:5000 --restart=always  --name registry  -v "$(pwd)"/data/registry:/var/lib/registry registry:2

vim /etc/docker/daemon.json

{ "insecure-registries":["192.168.241.131:5000"] }
