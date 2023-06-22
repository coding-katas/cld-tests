Start with docker
sudo systemctl stop zookeeper
sudo systemctl stop kafka
sudo systemctl start docker
sudo docker-compose up -d


Stop :
sudo docker-compose stop
sudo systemctl stop docker
