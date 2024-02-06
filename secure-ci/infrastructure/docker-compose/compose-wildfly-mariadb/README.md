# Docker Compose: Wildfly + MariaDB

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
After defining the `docker-compose.yml` file, we can start this environment:
```
$ sudo docker-compose up
```
Now, we can access wildfly via `http://localhost:8080` and open the **Administration Console** (authenticate via admin/admin).
Follow the Links `Configuration / Subsystems / Datasources & Drivers / Datasources` we can review the datasource settings.

We can shutdown the whole environemnt by pressing `Ctrl-C`.

Using `docker-compose ps` we can list all Docker containers which have been used in the `docker-compose.yml` file.
Thus, we can focus on the containers that make up the environment we are currently working on.

The first step in cleaning up is to stop the environemnt. We can use `docker-compose stop`.
Once we have stopped the containers, we can clean up with the `docker-compose rm` command.
The difference to the Docker `rm` command is that `docker-compose rm` will remove all containers defined by the environment.

## References
* [Introduction to Docker Compose](https://www.baeldung.com/docker-compose)

*Egon Teiniker, 2021-2022, GPL v3.0*
