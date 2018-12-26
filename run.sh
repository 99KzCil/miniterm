git pull
mvn clean package
docker-compose down && docker-compose up -d
tail -f app.log