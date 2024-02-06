# Building a Docker Image with Wildfly

A **Dockerfile** is a script that describes steps for Docker to take to build a new image.
These files are distributed along with software that the author wants to be put into an image.
A common pattern is to distribute a Dockerfile together with the source code in a version-control system
like Git.

Given the following directory:
```
wildfly
├── com
│   └── mysql
│       └── main
│           ├── module.xml
│           └── mysql-connector-java-5.1.24-bin.jar
├── Dockerfile
└── standalone.xml
```
The following **Dockerfile** configures the original **jboss/wildfly** image to add an **admin** user, add the **JDBC module**, 
and override the **standalone.xml** file.
```
FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin root66 --silent
COPY ./com/ /opt/jboss/wildfly/modules/com/
COPY mgmt-users.properties /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
```
We use the `docker build` command to create a new image:
```
$ cd wildfly
$ sudo docker build -t wildfly .
```

Now, we can see the new entry in the list of images:
```
$ sudo docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
wildfly             latest              1eb1b09dc242        5 seconds ago       712MB
jboss/wildfly       latest              ca8e39f8495f        2 days ago          711MB
```

Thus, we can run this new image in a container:
```
$ sudo docker container run -p 8080:8080 -p 9990:9990 wildfly
```
Note that the new container provides our settings (Datasource and Admin user).

## References
* [Best practices for writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)

*Egon Teiniker, 2021-2022, GPL v3.0*
