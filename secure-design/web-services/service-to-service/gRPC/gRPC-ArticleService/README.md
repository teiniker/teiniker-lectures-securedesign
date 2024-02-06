# gRPC ArticleService

This gRPC ArticleService implementation was made by **Armin Schaberl (IMS19)**.

## gRPC Stubs
This example uses the 'protobuf-maven-plugin' plugin to generate gRPC Stubs within the maven build.
The generator hooks into the maven compile and compile-custom goal.

## Build & Start
The project can be built using the maven command line tool:
```
mvn compile
```

### Starting the Server
The following commands can be used to start the gRPC Article Server:
```
./start-server.sh
```
or
```
mvn exec:java -D"exec.mainClass"="org.se.lab.server.GrpcServer"
```
A successful startup of the `GprcServer` is indicated by the console output 
`[main] INFO org.se.lab.server.GrpcServer - Article Server started...`. 
The server is then listening on port 8081 for incoming requests.

### Starting the Client
The following commands can be used to start the gRPC Article Client:
```
./start-client.sh
```
or
```
mvn exec:java -D"exec.mainClass"="org.se.lab.client.GrpcClient"
```
