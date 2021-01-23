# Example: Docker Compose (python + flask + redis)

This example is based on the [Get started with Docker Compose](https://docs.docker.com/compose/gettingstarted/)
article published on the Docker homepage.

## Create a Dockerfile

We want to setup the infrastructure for a Python Service based on Flask and Redis, an in-memory data structure store.

First, we create a `docker-flask` directory:
```
docker-flask/
├── Dockerfile
├── requirements.txt
└── service.py
```
The **web service implementation** is stored in `service.py`. 

The **Python packaes** which must be installed are listed in `requirements.txt`:
```
flask
redis
```

Finally, we write a **Dockerfile** that uses the `python:3.7-alpine` base image:
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
Within this `Dockerfile`, the following steps will be performed:
* Build an image starting with the Python 3.7 image.
* Set the working directory to `/code`.
* Set environment variables used by the flask command.
* Install gcc and other dependencies using `apk`
* Copy `requirements.txt` and install the Python dependencies.
* Add metadata to the image to describe that the container is listening on port 5000
* Copy the current directory . in the project to the workdir . in the image.
* Set the default command for the container to flask run.


## Create a Compose File
Because we want to use the Flask image together with Redis, we use the following `docker-compose.yml` file,
stored in a top-level directory called `compose-flask`:
```
compose-flask/
├── docker-compose.yml
├── docker-flask
└── README.md
```
This Compose file defines two services: `web` and `redis`:
```
version: "3.9"
services:
  web:
    build: ./docker-flask
    ports:
      - "8080:5000"
    volumes:
      - ./docker-flask:/code
    environment:
      FLASK_ENV: development
  redis:
    image: "redis:alpine"
```
The `web` service uses an image that’s built from the `Dockerfile`. 
It then binds the container port 5000 to the host port 8080. 
 
The `redis` service uses a public Redis image pulled from the Docker Hub registry.
  
Note that we have mount the host's directory `./docker-flask` to the in container directory `/code`
by using the `volumes` keyword.
This allows us to **modify the code on the fly**, without having to rebuild the image.

The `environment` key sets the `FLASK_ENV` environment variable, which tells flask run to run in **development mode** 
and reload the code on change. This mode should only be used in development.

## Run the Service
From our project directory, we start up your application:
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
* [Get started with Docker Compose](https://docs.docker.com/compose/gettingstarted/)
* [Flask](https://flask.palletsprojects.com/en/1.1.x/)
* [Redis](https://redis.io/)