docker build . -t blogservice
docker stop blogservice
docker rm blogservice
docker run -d --name=blogservice -p 8080:8080 blogservice