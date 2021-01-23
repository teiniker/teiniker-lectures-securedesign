# Example: Docker Compose (python + flask + redis)

## Create a Dockerfile

We write a **Dockerfile** that uses the `python:3.7-alpine` base image:
```
FROM python:3.7-alpine
WORKDIR /code
ENV FLASK_APP=service.py
ENV FLASK_RUN_HOST=0.0.0.0
RUN apk add --no-cache gcc musl-dev linux-headers
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
CMD ["flask", "run"]
```
Within this Dockerfile, the following steps will be performed:


## Create a Compose File
```
version: "3.9"
services:
  web:
    build: ./docker-flask
    ports:
      - "5000:5000"
  redis:
    image: "redis:alpine"
```
    
## Run the Service
    
```
# cd compose-flask

# docker-compose up
```

Now we can use curl to access the service:
```
$ curl -i http://localhost:8080

HTTP/1.0 200 OK
Content-Type: text/html; charset=utf-8
Content-Length: 39
Server: Werkzeug/1.0.1 Python/3.7.9
Date: Sat, 23 Jan 2021 07:52:29 GMT

Hello World! I have been seen 1 times.
```

Using the `docker` command, we can analyse the installed images and running containers:

```
# docker image ls 

REPOSITORY                 TAG                 IMAGE ID            CREATED             SIZE
compose-flask_web          latest              b72cc165c386        15 minutes ago      196MB
redis                      alpine              933c79ea2511        9 days ago          31.6MB
python                     3.7-alpine          72e4ef8abf8e        5 weeks ago         41.1MB
```

```
# docker container ls

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
c5e772c5cb2e        redis:alpine        "docker-entrypoint.s…"   5 minutes ago       Up 5 minutes        6379/tcp                 compose-flask_redis_1
17cfd8800d30        compose-flask_web   "flask run"              5 minutes ago       Up 5 minutes        0.0.0.0:8080->5000/tcp   compose-flask_web_1
```


## References
[Get started with Docker Compose](https://docs.docker.com/compose/gettingstarted/)
