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

*Egon Teiniker, 2021, GPL v3.0*
