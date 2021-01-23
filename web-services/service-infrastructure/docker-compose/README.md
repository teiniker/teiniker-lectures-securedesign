# Docker-Compose
Compose is a tool for defining, launching, and managing services, where service is defined as one or more replicas of a 
Docker container.
Services and systems of services are defined in **YAML files** (docker-compose.yml) and managed with the command-line 
program `docker-compose`.

Docker-Compose lets us stop focusing on individual containers and instead **describe full environments** and 
**service component interactions**. 
With Compose we can use simple commands to accomplish these tasks:
* Build Docker images
* Launch containerized applications as services
* Launch full systems of services
* Manage the state of individual services in a system
* Scale services up or down
* View logs for the collection of containers making a service

A Compose file might describe four or five unique services that are interrelated but should maintain isolation and may 
scale independently. Thus, most interactions with Docker will be through Compose.

From within the **directory which contains the docker-compose-yml file**, we can manage this system of containers with 
the following commands:
```
# docker-compose up         Create and start containers

# docker-compose ps         List containers

# docker-compose stop       Stop services
# docker-compose start      Start services

# docker-compose rm         Remove stopped containers

# docker-compose down       Stop all containers and remove them
```

## YAML Configuration 
Docker Compose works by applying many rules declared within a single `docker-compose.yml` configuration file.
The following descriptions are mainly out of the following article: [Introduction to Docker Compose](https://www.baeldung.com/docker-compose) 

In Docker Compose, **Services** are the building blocks and refer to Docker container configurations. 
A `docker-compose.yml` file usually defines many services:
```
services:
  frontend:
    image: my-vue-app
    ...
  backend:
    image: my-springboot-app
    ...
  db:
    image: postgres
    ...
``` 
There are multiple settings that we can apply to services.

### Configuring Images
Sometimes, the **image** we need for our service has already been published (by us or by others) in Docker Hub, or another Docker Registry.
If that's the case, then we can **pull an image** using the `image` attribute, by specifying the image name and tag:
```
services: 
  my-service:
    image: ubuntu:latest
    ...
```

Instead, we might need to **build an image** from the source code by reading its `Dockerfile`.
This time, we'll use the build keyword, passing the path to the Dockerfile as the value:
```
services: 
  my-custom-app:
    build: /path/to/dockerfile/
    ...
```

Note that we can also use a URL instead of a path.
Additionally, we can specify an image name in conjunction with the build attribute, which will name the image once created: 
```
services: 
  my-custom-app:
    build: https://github.com/my-company/my-project.git
    image: my-project-image
    ...
```

### Configuring the Network
Docker containers communicate between themselves in networks created, implicitly or through configuration, by Docker 
Compose. A service can communicate with another service on the same network by simply referencing it by **container name 
and port**, provided that we have made the port accessible through the `expose` keyword:
```
services:
  network-example-service:
    image: karthequian/helloworld:latest
    expose:
      - "80"
```

To **reach a container from the host**, the ports must be exposed declaratively through the `ports` keyword, which also 
allows us to choose the **port differently in the host**. 
This powerful mechanism allows us to run different containers exposing the same ports without collisions.
```
services:
  my-custom-app:
    image: myapp:latest
    ports:
      - "8080:3000"
    ...
```

Finally, we can define **additional virtual networks** to segregate our containers:
```
services:
  network-example-service:
    image: karthequian/helloworld:latest
    networks: 
      - my-shared-network
    ...
  another-service-in-the-same-network:
    image: alpine:latest
    networks: 
      - my-shared-network
    ...
  another-service-in-its-own-network:
    image: alpine:latest
    networks: 
      - my-private-network
    ...
networks:
  my-shared-network: {}
  my-private-network: {}
```
We can see that a`nother-service-in-the-same-network` will be able to ping and to reach port 80 of 
`network-example-service`, while `another-service-in-its-own-network` won't.

### Configuring Volumes
There are three types of volumes:
* Docker manages both **anonymous and named volumes**, automatically mounting them in self-generated directories 
  in the host. While anonymous volumes were useful with older versions of Docker (pre 1.9), named ones are the 
  suggested way to go nowadays. 
* **Host volumes** allow us to specify an existing folder in the host.

We can configure **host volumes at the service level** and **named volumes in the outer level** of the configuration, 
in order to make the latter visible to other containers and not only to the one they belong:
```
services:
  volumes-example-service:
    image: alpine:latest
    volumes: 
      - my-named-global-volume:/my-volumes/named-global-volume
      - /tmp:/my-volumes/host-volume
      - /home:/my-volumes/readonly-host-volume:ro
    ...
  another-volumes-example-service:
    image: alpine:latest
    volumes:
      - my-named-global-volume:/another-path/the-same-named-global-volume
    ...
volumes:
  my-named-global-volume: 
```
Both containers will have read/write access to the `my-named-global-volume` shared folder, no matter the different 
paths they've mapped it to. 

The two host volumes, instead, will be available only to `volumes-example-service`.

The `/tmp` folder of the host's file system is mapped to the `/my-volumes/host-volume` folder of the container.
This portion of the file system is writeable, which means that the container can not only read but also write 
(and delete) files in the host machine.

We can mount a volume in read-only mode by appending `:ro` to the rule, like for the `/home` folder.

### Configuring Dependencies
Often, we need to create a **dependency chain between our services**, so that some services get loaded before 
(and unloaded after) other ones. We can achieve this result through the `depends_on` keyword:
```
services:
  kafka:
    image: wurstmeister/kafka:2.11-0.11.0.3
    depends_on:
      - zookeeper
    ...
  zookeeper:
    image: wurstmeister/zookeeper
    ...
```
Note that Compose will not wait for the `zookeeper` service to finish loading before starting the `kafka` service: 
it will simply wait for it to start. 

### Configuring Environment Variables
We can define **static environment variables**, and also define **dynamic variables** with the `${}` notation:
```
services:
  database: 
    image: "postgres:${POSTGRES_VERSION}"
    environment:
      DB: mydb
      USER: "${USER}"
```
There are different methods to provide those values to Compose.
* We can  setting them in a **.env file** in the same directory, structured like a `.properties` file, `key=value`
    ```
    POSTGRES_VERSION=alpine
    USER=foo
    ```
* We can **set them in the OS** before calling the command:
    ```
    export POSTGRES_VERSION=alpine
    export USER=foo
    docker-compose up
    ```
    We might find handy using a simple one-liner in the shell:
    ```
    POSTGRES_VERSION=alpine USER=foo docker-compose up
    ```

We can mix the approaches, but let's keep in mind that Compose uses the following **priority order**, overwriting the 
less important with the higher ones:
1. Compose file
2. Shell environment variables
3. Environment file
4. Dockerfile
5. Variable not defined


## Example: Wildfly + MariaDB

Given the following directory tree:
```
docker-compose
├── docker-compose.yml
└── wildfly
    ├── com
    │         └── mysql
    │             └── main
    │                 ├── module.xml
    │                 └── mysql-connector-java-5.1.24-bin.jar
    ├── Dockerfile
    └── standalone.xml
```

The **docker-compose.yml** file defines a system of two containers (mariadb and wildfly).
Note that the wildfly image will be build from a Dockerfile which contains additional settings for the application server. 
```
version: '2.1'

services:
  wildfly:
    build: wildfly/.
    depends_on:
      mariadb:
        condition: service_healthy
    ports:
      - '8080:8080'
      - '9990:9990'

  mariadb:
    image: mariadb
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: test
      MYSQL_DATABASE: testdb
      MYSQL_USER: student
      MYSQL_PASSWORD: student
    healthcheck:
      test: ["CMD", "mysqladmin" ,"ping", "--silent"]
      timeout: 20s
      retries: 10

```
We start Docker Compose in the directory where we have stored the `docker-compose.yml` file:
```
# cd docker-compose
# docker-compose up
...
# docker-compose ps
          Name                        Command                  State                           Ports                     
-------------------------------------------------------------------------------------------------------------------------
docker-compose_mariadb_1   docker-entrypoint.sh mysqld      Up (healthy)   0.0.0.0:3306->3306/tcp                        
docker-compose_wildfly_1   /opt/jboss/wildfly/bin/sta ...   Up             0.0.0.0:8080->8080/tcp, 0.0.0.0:9990->9990/tcp
```
After defining the `docker-compose.yml` file, we can start this environment with `docker-compose up`.
Now, we can access wildfly via `http://localhost:8080` and open the **Administration Console** (authenticate via admin/admin).
Follow the Links `Configuration / Subsystems / Datasources & Drivers / Datasources` we can review the datasource settings.

We can shutdown the whole environemnt by pressing `Ctrl-C`.

Using `docker-compose ps` we can list all Docker containers which have been used in the `docker-compose.yml` file.
Thus, we can focus on the containers that make up the environment we are currently working on.

The first step in cleaning up is to stop the environemnt. We can use `docker-compose stop`.
Once we have stopped the containers, we can clean up with the `docker-compose rm` command.
The difference to the Docker `rm` command is that `docker-compose rm` will remove all containers defined by the environment.

## References
* [Overview of Docker Compose](https://docs.docker.com/compose/)
* Jeff Nickoloff. **Docker in Action**. Manning, 2016 
* [Introduction to Docker Compose](https://www.baeldung.com/docker-compose)

*Egon Teiniker, 2021, GPL v3.0*
