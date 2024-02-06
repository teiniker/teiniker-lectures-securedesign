# Example: gRPC Client / Server 

The given example shows the implementation of a simple `HelloService` using gRPC.

## Defining the Service
We define a service by specifying **methods** that can be called remotely along with their **parameters** 
and **return types**.

This is done in the **.proto file** using the **protocol buffers**. 
They are also used for describing the structure of the payload messages.

```
syntax = "proto3";
option java_multiple_files = true;
package org.se.lab;

message HelloRequest
{
    string firstName = 1;
    string lastName = 2;
}

message HelloResponse
{
    string greeting = 1;
}

service HelloService
{
    rpc hello(HelloRequest) returns (HelloResponse);
}
```

The **first line** tells the compiler what syntax is used in this file. 
By default, the compiler generates all the Java code in a single Java file. 

The **second line** overrides this setting, and everything will be generated in individual files.

In the **third line**, we specify the package we want to use for our generated Java classes.

Next, we define the **messages** used as payload for request and response.
Each **attribute** that goes into the message is defined along with its type.
A **unique number** needs to be assigned to each attribute, called as the **tag**. 
This tag is used by the protocol buffer to represent the attribute instead of using the attribute name.
Unlike JSON where we would pass attribute name firstName every single time, protocol buffer would 
use the number 1 to represent firstName. Response payload definition is similar to the request.

Finally, we define the **service contract**. For our `HelloService` we define a `hello()` operation.
The `hello()` operation accepts a **unary request** and returns a **unary response**. 
gRPC also supports **streaming** by prefixing `stream` keyword to the request and response.

## Creating the Server

Using a **code generator**, the following files will be generated:
* **HelloRequest.java**: Contains the `HelloRequest` type definition
* **HelloResponse.java**: This contains the `HelleResponse` type definition
* **HelloServiceImplBase.java**: This contains the abstract class `HelloServiceImplBase` which provides 
    an implementation of all the operations we defined in the service interface.

We inherit the `HelloServiceImplBase` class and override the `hello()` method mentioned in our 
service definition:
```Java
public class HelloServiceImpl
    extends HelloServiceGrpc.HelloServiceImplBase
{
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver)
    {
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```
If we compare the signature of `hello()` with the one we wrote in the `HellService.proto` 
file, we'll notice that it does not return `HelloResponse`. 
Instead, it takes the second argument as `StreamObserver<HelloResponse>`, which is a 
response observer, a call back for the server to call with its response.
This way the client gets an option to make a **blocking call** or a **non-blocking call**.

gRPC uses **builders** for creating objects. 
We use `HelloResponse.newBuilder()` and set the greeting text to build a `HelloResponse` object. 
We set this object to the responseObserver's `onNext()` method to send it to the client.

Finally, we need to call `onCompleted()` to specify that we’ve finished dealing with the RPC, 
else the connection will be hung, and the client will just wait for more information to come in.

We also need to start the **gRPC server** to listen for incoming requests:
```Java
public class GRPCServer
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloServiceImpl()).build();

        server.start();
        System.out.println("Server started!");

        server.awaitTermination();
    }
}
```
We use the **builder** to create a gRPC server on **port 8080** and add the `HelloServiceImpl` 
service that we defined. `start()` would start the server. 

We will call `awaitTermination()` to keep the server running in the foreground blocking the 
prompt. 

 
## Creating the Client
gRPC provides a **channel construct** that abstracts out the underlying details like connection, 
connection pooling, load balancing, etc.

```Java
@Test
public void testHelloService()
{
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();

    HelloServiceGrpc.HelloServiceBlockingStub stub
            = HelloServiceGrpc.newBlockingStub(channel);

    HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
            .setFirstName("Homer")
            .setLastName("Simpson")
            .build());

    System.out.println("Response received from server:\n" + helloResponse);
    channel.shutdown();
}
```
We need to create a **stub** which we can use to make the actual remote call to `hello()`. 
**The stub is the primary way for clients to interacts with the server.** 
When using auto-generated stubs, the stub class will have constructors for wrapping the channel.

Here we are using a **blocking/synchronous** stub so that the RPC call waits for the server to 
respond, and will either return a response or raise an exception.

## References
* [Introduction to Google Protocol Buffer](https://www.baeldung.com/google-protocol-buffer)
* [Introduction to gRPC](https://www.baeldung.com/grpc-introduction)

*Egon Teiniker, 2020-2021, GPL v3.0*