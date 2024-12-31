# SpringBoot Actuator Framework

## Setup

The **Actuator framework** can easily be **added to an existing REST service**.

To do this, add the following dependency in the **pom.xml** file:

```XML
		<dependency> 
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```

Note that the version number is inherited from the SpringBoot framework.

In the **application.properties** file, the following entries enable all 
/actuator endpoints:

```
management.endpoints.web.exposure.include=*
management.endpoint.env.enabled=true
management.endpoint.env.show-values=ALWAYS
```

We use Maven to compile and run the service:

```Bash
$ mvn spring-boot:run
```

To achieve better **formatting of the JSON content**, we use the command line tool **jq**:

```Bash
$ sudo apt install jq
```


## Using Actuator Endpoints 

```Bash
$ curl http://localhost:8080/actuator | jq
{
    "_links": {
        "self": {
        "href": "http://localhost:8080/actuator",
        "templated": false
        },
        "beans": {
        "href": "http://localhost:8080/actuator/beans",
        "templated": false
        },
        "caches-cache": {
        "href": "http://localhost:8080/actuator/caches/{cache}",
        "templated": true
        },
        "caches": {
        "href": "http://localhost:8080/actuator/caches",
        "templated": false
        },
        "health": {
        "href": "http://localhost:8080/actuator/health",
        "templated": false
        },
        "health-path": {
        "href": "http://localhost:8080/actuator/health/{*path}",
        "templated": true
        },
        "info": {
        "href": "http://localhost:8080/actuator/info",
        "templated": false
        },
        "conditions": {
        "href": "http://localhost:8080/actuator/conditions",
        "templated": false
        },
        "configprops": {
        "href": "http://localhost:8080/actuator/configprops",
        "templated": false
        },
        "configprops-prefix": {
        "href": "http://localhost:8080/actuator/configprops/{prefix}",
        "templated": true
        },
        "env": {
        "href": "http://localhost:8080/actuator/env",
        "templated": false
        },
        "env-toMatch": {
        "href": "http://localhost:8080/actuator/env/{toMatch}",
        "templated": true
        },
        "loggers": {
        "href": "http://localhost:8080/actuator/loggers",
        "templated": false
        },
        "loggers-name": {
        "href": "http://localhost:8080/actuator/loggers/{name}",
        "templated": true
        },
        "heapdump": {
        "href": "http://localhost:8080/actuator/heapdump",
        "templated": false
        },
        "threaddump": {
        "href": "http://localhost:8080/actuator/threaddump",
        "templated": false
        },
        "metrics-requiredMetricName": {
        "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
        "templated": true
        },
        "metrics": {
        "href": "http://localhost:8080/actuator/metrics",
        "templated": false
        },
        "sbom": {
        "href": "http://localhost:8080/actuator/sbom",
        "templated": false
        },
        "sbom-id": {
        "href": "http://localhost:8080/actuator/sbom/{id}",
        "templated": true
        },
        "scheduledtasks": {
        "href": "http://localhost:8080/actuator/scheduledtasks",
        "templated": false
        },
        "mappings": {
        "href": "http://localhost:8080/actuator/mappings",
        "templated": false
        }
    }
}
```

### Health Endpoint

```Bash
$ curl http://localhost:8080/actuator/health | jq

{
    "status": "UP"
}
```

### Metrics Endpoints

```Bash
$ curl http://localhost:8080/actuator/metrics | jq
{
    "names": [
    "application.ready.time",
    "application.started.time",
    "disk.free",
    "disk.total",
    "executor.active",
    "executor.completed",
    "executor.pool.core",
    "executor.pool.max",
    "executor.pool.size",
    "executor.queue.remaining",
    "executor.queued",
    "hikaricp.connections",
    "hikaricp.connections.acquire",
    "hikaricp.connections.active",
    "hikaricp.connections.creation",
    "hikaricp.connections.idle",
    "hikaricp.connections.max",
    "hikaricp.connections.min",
    "hikaricp.connections.pending",
    "hikaricp.connections.timeout",
    "hikaricp.connections.usage",
    "http.server.requests.active",
    "jdbc.connections.active",
    "jdbc.connections.idle",
    "jdbc.connections.max",
    "jdbc.connections.min",
    "jvm.buffer.count",
    "jvm.buffer.memory.used",
    "jvm.buffer.total.capacity",
    "jvm.classes.loaded",
    "jvm.classes.unloaded",
    "jvm.compilation.time",
    "jvm.gc.concurrent.phase.time",
    "jvm.gc.live.data.size",
    "jvm.gc.max.data.size",
    "jvm.gc.memory.allocated",
    "jvm.gc.memory.promoted",
    "jvm.gc.overhead",
    "jvm.gc.pause",
    "jvm.info",
    "jvm.memory.committed",
    "jvm.memory.max",
    "jvm.memory.usage.after.gc",
    "jvm.memory.used",
    "jvm.threads.daemon",
    "jvm.threads.live",
    "jvm.threads.peak",
    "jvm.threads.started",
    "jvm.threads.states",
    "logback.events",
    "process.cpu.time",
    "process.cpu.usage",
    "process.files.max",
    "process.files.open",
    "process.start.time",
    "process.uptime",
    "spring.data.repository.invocations",
    "system.cpu.count",
    "system.cpu.usage",
    "system.load.average.1m",
    "tomcat.sessions.active.current",
    "tomcat.sessions.active.max",
    "tomcat.sessions.alive.max",
    "tomcat.sessions.created",
    "tomcat.sessions.expired",
    "tomcat.sessions.rejected"
]
}
```

```Bash
$ curl http://localhost:8080/actuator/metrics/jvm.info | jq
{
    "name": "jvm.info",
    "description": "JVM version info",
    "measurements": [
    {
    "statistic": "VALUE",
    "value": 1
    }
    ],
    "availableTags": [
    {
    "tag": "vendor",
    "values": [
    "Oracle Corporation"
    ]
    },
    {
    "tag": "runtime",
    "values": [
    "OpenJDK Runtime Environment"
    ]
    },
    {
    "tag": "version",
    "values": [
    "21+35-2513"
    ]
    }
    ]
}
```

### Info Endpoint

```Bash
$ curl http://localhost:8080/actuator/info | jq
```

### Environment Endpoint

```Bash
$ curl http://localhost:8080/actuator/env | jq
{
    "activeProfiles": [],
    "defaultProfiles": [
        "default"
    ],
    "propertySources": [
    {
        "name": "server.ports",
        "properties": {
            "local.server.port": {
            "value": 8080
            }
        }
    },
    {
        "name": "servletContextInitParams",
        "properties": {}
    },
    {
        "name": "systemProperties",
        "properties": {
            "java.specification.version": {
                "value": "21"
        },
        "sun.jnu.encoding": {
            "value": "UTF-8"
        },
        ...
    }
}
```

### Heap Dump Endpoint 

```Bash
$ curl http://localhost:8080/actuator/heapdump --output dump
% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
Dload  Upload   Total   Spent    Left  Speed
100 50.6M  100 50.6M    0     0  48.9M      0  0:00:01  0:00:01 --:--:-- 48.9M
```

To ańalyze the downloaded heapdump, we can use the **ViaualVM** tool: 

```Bash
$ cd /opt/visualvm_2110/
$ bin/visualvm
    File | Load => dump
```


## Using the Article Service

The `/articles` service is used here only as an example. 
The focus is on the `/actuator` endpoints.

### Find Articles

```
$ curl -i http://localhost:8080/articles
```

```
$ curl -i http://localhost:9090/articles/2
```

```
$ curl -i http://localhost:9090/articles/99
```

### Insert an Article
```
$ curl -i -X POST http://localhost:9090/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'
```

### Update an Article
```
$ curl -i -X PUT http://localhost:9090/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'
```

### Delete an Article
```
$ curl -i -X DELETE http://localhost:9090/articles/3
```


## References

* [SpringBoot References: Actuator](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html)

* [38C3: Wir wissen wo dein Auto steht - Volksdaten von Volkswagen](https://media.ccc.de/v/38c3-wir-wissen-wo-dein-auto-steht-volksdaten-von-volkswagen)

*Egon Teiniker, 2016-2024, GPL v3.0*