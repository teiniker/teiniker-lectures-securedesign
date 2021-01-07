# Docker-Compose
Compose is a tool for defining, launching, and managing services, where service is defined as one or more replicas of a 
Docker container.
Services and systems of services are defined in **YAML files** (docker-compose.yml) and managed with the command-line 
program `docker-compose`.

Docker-Compose lets you stop focusing on individual containers and instead **describe full environments** and 
**service component interactions**. 

## Example: Wildfly + MariaDB

Given the following directory tree:
```
Docker
├── docker-compose.yml
└── wildfly
    ├── com
    │   └── mysql
    │       └── main
    │           ├── module.xml
    │           └── mysql-connector-java-5.1.24-bin.jar
    ├── Dockerfile
    └── standalone.xml
```

The **docker-compose.yml** file defines a system of two containers (mariadb and wildfly).
Note that the wildfly image will be build from a Dockerfile which contains additional settings for the application server. 
```
version: '2.1'

services:
  wildfly:
    build: docker/wildfly/.
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

From within the **directory which contains the docker-compose-yml file**, we can manage this system of containers with 
the following commands:
```
# cd Docker

# docker-compose up         Create and start containers

# docker-compose ps         List containers

# docker-compose stop       Stop services
# docker-compose start      Start services

# docker-compose rm         Remove stopped containers

# docker-compose down       Stop all containers and remove them
```

## References

* [Overview of Docker Compose](https://docs.docker.com/compose/)
* Jeff Nickoloff. **Docker in Action**. Manning, 2016 

*Egon Teiniker, 2021, GPL v3.0*
