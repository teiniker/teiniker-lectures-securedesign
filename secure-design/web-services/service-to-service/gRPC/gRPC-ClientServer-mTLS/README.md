# Example: gRPC using mTLS (Client & Server) 

This example secures the communication between a gRPC client and its server using mutual TLS (mTLS).

We start with a working gRPC example (see: gRPC-ClientServer), and modify the configurations needed to
add mTLS.

## Client and Server Certificates

In order to communicate via mTLS, client and server needs a valid **certificate signed by a CA**.
To make things easy, we use a shell script to invoke **OpenSSL** many times to create all keys and certificates.
These files are stored in the `/tmp/sslcert/` directory:
```
$ ./mkcerts.sh
 
$ tree /tmp/sslcert
/tmp/sslcert
├── ca.crt
├── ca.key
├── client.crt
├── client.csr
├── client.key
├── client.pem
├── server.crt
├── server.csr
├── server.key
└── server.pem
```
The important files for this example are:
* **ca.key** Private key of the certificate Authority (CA).
* **ca.crt** Certificate of the CA, self-signed.
* **server.pem** Private key of the server.
* **server.crt** Certificate of the server, signed by the CA.
* **client.pem** Private key of the client.
* **client.crt** Certificate of the client, signed by the CA.

## Server Implementation

The following code shows how a gRPC server must be configured to support mTLS.
Here, we have hardcoded the paths to the generated certificates, we could also use command line parameters instead.

```Java
    public static void main(String[] args) throws IOException, InterruptedException
    {
        SslContextBuilder sslServerContextBuilder = SslContextBuilder.forServer(new File("/tmp/sslcert/server.crt"),
                new File("/tmp/sslcert/server.pem"))
                .trustManager(new File("/tmp/sslcert/ca.crt"))
                .clientAuth(ClientAuth.REQUIRE);

        SslContext sslContext = GrpcSslContexts.configure(sslServerContextBuilder, SslProvider.OPENSSL).build();

        Server server = NettyServerBuilder.forAddress(new InetSocketAddress("localhost",8443))
                .addService(new HelloServiceImpl())
                .sslContext(sslContext)
                .build()
                .start();
        LOG.info("Server started...");

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                server.shutdown();
                System.err.println("*** server shut down");
            }
        });

        server.awaitTermination();
    }
```

First, an **SSL context** is configured by reading the server's certificate and private key. The CA certificate is 
also loaded.
Client authentication will be enforced to ensure a mutual TLS connection.

The **server** object is based on the Netty framework and is configured to listen on port 8443. 
The service implementation is attached to the server and so is the SSL context.
At the end, the server will be started.

Finally, the **awaitTermination()** method waits for the server to become terminated.

## Client Implementation
Also, the implementation of the gRPC client must be extended to support mTLS.

```Java
    public static void main(String[] args) throws Exception
    {
        SslContext sslContext = GrpcSslContexts.forClient()
                .trustManager(new File("/tmp/sslcert/ca.crt"))
                .keyManager(new File("/tmp/sslcert/client.crt"), new File("/tmp/sslcert/client.pem"))
                .build();

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 8443)
                .negotiationType(NegotiationType.TLS)
                .sslContext(sslContext)
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Homer")
                .setLastName("Simpson")
                .build());

        System.out.println("Response received from server:\n" + helloResponse.getGreeting());

        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
```

An **SSL context** is configured by loading the client's certificate and private key. The CA certificate is
also used.

A **TLS channel** ,based on the Netty framework,  is configured to connect to localhost port 8443.

The **generated stub** uses this channel to delegate method invocations to the server-side and to receive
the operation's response.

On this stub, the hello() method will be invoked. A builder is used to create the request object. The response
object will be stored in the `helloResponse` variable.

Finally, the communication channel will be closed.

## Build and Run the Example

To run the proto generator and the Java compiler, we use a maven command:
```
$ mvn compile
```

Beacuse of the dependencies which are resolved within the IDE, we und the `GrpcServer` and `GrpcClient` directly
in the IDE (right-click Run).

## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020 
  * Chapter 8: Securing east/west traffic over gRPC
  
* [Netty Project](https://netty.io/)