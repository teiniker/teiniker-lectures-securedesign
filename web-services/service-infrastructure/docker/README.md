# Docker 

Docker is a command-line program, a background daemon, and a set of remote services.
Docker helps to simplify tasks like installing, running, publishing, and removing software.

UNIX-style OS have used the term *jail* to describe a modified runtime environment for a program that prevents that 
program from accessing protected resources. Later the term container was used for this type of environment.
Any software run with Docker is run inside a **Docker Container**.

**Virtual machines (VM)** provide virtual hardware on which an operating system and other programs can be installed.
Docker containers don't use hardware virtualization. Programs running inside Docker containers interface directly with 
the host's Linux kernel and can access only their own memory and resources as scoped by the container.

Docker runs natively on Linux and comes with a single virtual machine for OS X and Windows environments. 
Thus, software running in Docker containers need only be written once against a consistent set of dependencies.

Docker improves the portability of every program regardless of the language it was written in, the operating system it 
was designed for, or the state of the environment where it is running.

Docker completes the traditional container metaphor by including a way to package and distribute software - called a 
**Docker Image**.
A Docker image is a bundled snapshot of all the files that should be available to a program running inside a container.
We can create as many containers from an image as we want.
Containers that were started from the same image don't share changes to their file system.

When we distribute software with Docker, we distribute these images, and the receiving computers create containers from them.

Docker provides a set of infrastructure components that simplify distributing Docker images called registries and indexes.

## Using Docker

Note that Docker runs as the root user on our system (Debian 10).
```
$ su

# systemctl start docker
# systemctl stop docker

# docker version
# docker info
```

A **Docker Container** is an instance of that image running as a process.
There are many container specific commands we can use:

```
# docker container help
Commands:
  attach      Attach local standard input, output, and error streams to a running container
  commit      Create a new image from a container's changes
  cp          Copy files/folders between a container and the local filesystem
  create      Create a new container
  diff        Inspect changes to files or directories on a container's filesystem
  exec        Run a command in a running container
  export      Export a container's filesystem as a tar archive
  inspect     Display detailed information on one or more containers
  kill        Kill one or more running containers
  logs        Fetch the logs of a container
  ls          List containers
  pause       Pause all processes within one or more containers
  port        List port mappings or a specific mapping for the container
  prune       Remove all stopped containers
  rename      Rename a container
  restart     Restart one or more containers
  rm          Remove one or more containers
  run         Run a command in a new container
  start       Start one or more stopped containers
  stats       Display a live stream of container(s) resource usage statistics
  stop        Stop one or more running containers
  top         Display the running processes of a container
  unpause     Unpause all processes within one or more containers
  update      Update configuration of one or more containers
  wait        Block until one or more containers stop, then print their exit codes
```

A **Docker Image** is the application we want to run. 
Also there are many commands we can use to manage images:

```
# docker image help
Commands:
  build       Build an image from a Dockerfile
  history     Show the history of an image
  import      Import the contents from a tarball to create a filesystem image
  inspect     Display detailed information on one or more images
  load        Load an image from a tar archive or STDIN
  ls          List images
  prune       Remove unused images
  pull        Pull an image or a repository from a registry
  push        Push an image or a repository to a registry
  rm          Remove one or more images
  save        Save one or more images to a tar archive (streamed to STDOUT by default)
  tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
```

Docker provides a mechanism for a user to inject environment variables into a new container.
The --env flag or -e for short can be used to inject any environment variable.

### Example: Wildfly
See: [DockerHub](https://hub.docker.com/r/jboss/wildfly)
```
# docker container run -p 8080:8080 -p 9990:9990 jboss/wildfly

The **run** command downloads the needed image from DockerHub (and all its dependencies) and starts a container running
the Wildfly application server.

# docker container ls -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
e25fd561accc        jboss/wildfly       "/opt/jboss/wildfly/…"   2 minutes ago       Up 2 minutes        8080/tcp            gracious_bassi

# docker image ls
REPOSITORY                               TAG                 IMAGE ID            CREATED             SIZE
jboss/wildfly                            latest              e6f71554a543        3 days ago          757MB
```

After starting Wildfly, we can access the application server using a Browser:
```
URL: http://localhost:8080/
```

We can manage the container using the **start** and **stop** commands.
```
# docker container stop e25fd561accc
e25fd561accc

# docker container ls -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                     PORTS               NAMES
e25fd561accc        jboss/wildfly       "/opt/jboss/wildfly/…"   5 minutes ago       Exited (0) 5 seconds ago                       gracious_bassi

# docker container start e25fd561accc

# ps -ax
27753 ?        Sl     0:00 containerd-shim -namespace moby -workdir /var/lib/containerd/io.containerd.runtime.v1.linux/moby/5f1f3d2d445a199a6c5b7f79fef50a7c8b40dc32e9060a84442c515f3f3e5a26 -
27772 ?        Ss     0:00 /bin/sh /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0
27889 ?        Sl     0:09 /usr/lib/jvm/java/bin/java -D[Standalone] -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.
27998 pts/3    R+     0:00 ps -ax
```
Note that the **Wildfly container is running as a process** in our Linux system.


### Example: Database
See: [DockerHub](https://hub.docker.com/_/mariadb)
```
# docker container run -d -p 3306:3306 --name db  --env="MYSQL_ROOT_PASSWORD=root66" mariadb

# docker container inspect db
...
"IPAddress": "172.17.0.2"
...

# mysql -uroot -proot66 -h 172.17.0.2
```


## Software Installation

### Docker Repositories

A **Docker Repository** is a named bucket of Docker images.
A repository's name is made up of the name of the host where the image is located, the user account that owns the image, 
and a short name.
As there can be several versions of software, a repository can hold several images.
Each of the images in a repository is identified uniquely with tags.
A repository name and tag form a composite key.

**Docker Indexes** are search engines that catalog repositories.
There are several public indexes, but by default Docker is integrated with an index named **Docker Hub**: 
`https://hub.docker.com`

Docker Hub makes Docker more useful out of the box.

We can use the following command to search the index:

```
    # docker search mariadb
    NAME                                   DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
    mariadb                                MariaDB is a community-developed fork of MyS…   3103                [OK]                
    bitnami/mariadb                        Bitnami MariaDB Docker Image                    107                                     [OK]
    linuxserver/mariadb                    A Mariadb container, brought to you by Linux…   92                                      
```

Docker Hub lets users star a repository, similar to a Facebook Like.

### Building a Docker Image with Wildfly
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
# cd wildfly
# docker build -t wildfly .
```

Now, we can see the new entry in the list of images:
```
# docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
wildfly             latest              1eb1b09dc242        5 seconds ago       712MB
jboss/wildfly       latest              ca8e39f8495f        2 days ago          711MB
```

Thus, we can run this new image in a container:
```
# docker container run -p 8080:8080 -p 9990:9990 wildfly
```
Note that the new container provides our settings (Datasource and Admin user).


## Image Layers

A **layer** is an image that is related to at least one other image.
When Docker went to install a dependency, it discovers the dependencies of that layer and downloads those first. 
Once all the dependencies of a layer are installed, that layer is installed.

By default, the docker images command will only show us repositories.
If we specify the -a flag, the list will include every installed intermediate image or layer.

Programs running inside containers know nothing about image layers.
From the perspective of the container, it has exclusive copies of the files provided by the image.
This is possible because of the so called **union file system**.
The file system is used to create mount points on our host's file system that abstracts the use of layers. 
When a Docker image is installed, its layers are unpacked and appropriately configured for the use by the specific 
file system.
The benefit is that common layers need to be installed only once.


## References
* [Docker Homepage](https://www.docker.com/)
* Jeff Nickoloff. **Docker in Action**. Manning, 2016 

*Egon Teiniker, 2021, GPL v3.0*
