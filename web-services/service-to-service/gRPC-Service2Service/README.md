# Securing Service-to-Service Communications Over gRPC

The following examples are reused from **chapter 8** of the **Microservices Security in Actions** book. 
You can find all examples of this book on [GitHub](https://github.com/microservices-security-in-action/samples).
This document describes the main steps needed to make the examples run and discusses the output 
of the experiments.

The following examples are based on the **Spring Boot** framework and can be build using **Maven**.


## Service-toService Communication over gRPC

The **Protocol Buffers IDL** is used to define the interface of the Inventory microservice in `src/main/proto`:
```
service Inventory 
{
    rpc UpdateInventory (Order) returns (UpdateReply) {}
}

message Order 
{
    int32 orderId = 1;
    repeated LineItem items = 2;
}

message LineItem 
{
    Product product = 1;
    int32 quantity = 2;
}

message Product 
{
    int32 id = 1;
    string name = 2;
    string category = 3;
    float unitPrice = 4;
}

message UpdateReply 
{
    string message = 1;
}
```

To generate the server and client stub classes and to compile the Java code we execute the following
commands:
```
$ cd sample01
$ ./gradlew installDist
```

The automatic generated Java classes can be found in `build/generated/source/proto`
```
build/generated/source/proto
└── main
    ├── grpc
    │        └── com
    │            └── manning
    │                └── mss
    │                    └── ch08
    │                        └── sample01
    │                            └── InventoryGrpc.java
    └── java
        └── com
            └── manning
                └── mss
                    └── ch08
                        └── sample01
                            ├── InventoryProto.java
                            ├── LineItem.java
                            ├── LineItemOrBuilder.java
                            ├── Order.java
                            ├── OrderOrBuilder.java
                            ├── Product.java
                            ├── ProductOrBuilder.java
                            ├── UpdateReply.java
                            └── UpdateReplyOrBuilder.java
``` 
 
To run the server, we execute in a new terminal:
``` 
$ cd teiniker-lectures-securedesign/web-services/service-to-service/gRPC-Service2Service/sample01
$ ./build/install/sample01/bin/inventory-server
INFO: Server started, listening on 50051
``` 

To run the client, we execute in another terminal:
``` 
$ cd teiniker-lectures-securedesign/web-services/service-to-service/gRPC-Service2Service/sample01
$ ./build/install/sample01/bin/inventory-client
INFO: Message: Updated inventory for 1 products
``` 

The inventory client program executed the `UpdateInventory()` RPC running on a different port/process.
```Java 
public class InventoryClient {
//...
   public static void main(String[] args) throws Exception 
   {

        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setCategory("books");
        product.setName("Microservices Security In Action");
        product.setUnitPrice(50.0f);

        LineItemEntity item = new LineItemEntity();
        item.setProduct(product);
        item.setQuantity(2);

        OrderEntity order = new OrderEntity();
        order.setOrderId(1);
        order.getItems().add(item);

        String hostname = System.getenv("INVENTORY_HOST");
        if (hostname==null || hostname.isEmpty()) 
        {
            hostname = "localhost";
        }

        String port = System.getenv("INVENTORY_PORT");
        if (port == null || port.isEmpty())
        {
            port = "50051";
        }
        InventoryClient client = new InventoryClient(hostname, Integer.parseInt(port));

        try 
        {
            client.updateInventory(order);
        } 
        finally 
        {
            client.shutdown();
        }
    }
//...
}
``` 

The server process received the message from the client and executed  its `UpdateReply` method to send back the reply.
```Java 
public class InventoryServer 
{
    private Server server;
//...
    public static void main(String[] args) throws IOException, InterruptedException 
    {
        final InventoryServer server = new InventoryServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException 
    {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new InventoryImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                InventoryServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

   private void blockUntilShutdown() throws InterruptedException 
   {

        if (server != null) {
            server.awaitTermination();
        }
    }

   static class InventoryImpl extends InventoryGrpc.InventoryImplBase 
   {
     @Override
        public void updateInventory(Order req, StreamObserver<UpdateReply> responseObserver) 
        {
            UpdateReply updateReply = UpdateReply.newBuilder().setMessage("Updated inventory for " + req.getItemsCount()
                    + " products").build();
            responseObserver.onNext(updateReply);
            responseObserver.onCompleted();
        }
    }
    //...
}
``` 

## Securing Communication over gRPC

**mTLS** allows us to build an explicit trust between the Order Processing microservice and 
Inventory microservice by **using certificates**.

To generate the server and client stub classes and to compile the Java code we execute the following
commands:
```
$ cd sample02
$ ./gradlew installDist
```

As the next step we need to create the certificates and keys required for the client
and the server microservices:
```
$ ./mkcerts.sh
$ ll /tmp/sslcert/
```
The shell script creates the required certificates in the `/tmp/sslcert` dicrectory.

We can **start the server** which hosts the Inventory microservice in a new terminal:
```
$ ./build/install/sample02/bin/inventory-server localhost 50440 /tmp/sslcert/server.crt /tmp/sslcert/server.pem /tmp/sslcert/ca.crt 
INFO: Server started, listening on 50440
```

The values of the parameters are:
* **localhost**: The host address to which the server process binds to.
* **50440**: The port on which the server starts.
* **tmp/sslcert/server.crt**: The certificate chain file of the server, which includes the server's public certificate.
* **/tmp/sslcert/server.pem**: The private key file of the server.
* **/tmp/sslcert/ca.crt**: The trust store collection file, which contains the certificates to be trusted by the server.

To **start the client**, we open a new terminal and execute the following command:
```
$ ./build/install/sample02/bin/inventory-client localhost 50440 /tmp/sslcert/ca.crt /tmp/sslcert/client.crt /tmp/sslcert/client.pem 
INFO: Message: Updated inventory for 1 products
```
We need to pass similar parameters to the client process as well:
* **localhost**: The host address of the server.
* **50440**: The port of the server.
* **tmp/sslcert/client.crt**: The certificate chain file of the client, which includes the clients's public certificate.
* **/tmp/sslcert/client.pem**: The private key file of the client.
* **/tmp/sslcert/ca.crt**: The trust store collection file, which contains the certificates to be trusted by the client.

Let's look at the source code to understand how we enabled mTLS on the server and client process.

```
InventoryServer.java
```

```
InventoryClient.java
```


If we try to run the client with just TLS (and not mTLS) we see the server's response:
```
$ ./build/install/sample02/bin/inventory-client localhost 50440 /tmp/sslcert/ca.crt  
WARNING: RPC failed: Status{code=UNAVAILABLE, description=io exception, cause=io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: localhost/[0:0:0:0:0:0:0:1]:50440
	at java.base/sun.nio.ch.Net.pollConnect(Native Method)
	at java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)
	at java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)
	at io.netty.channel.socket.nio.NioSocketChannel.doFinishConnect(NioSocketChannel.java:327)
	at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe.finishConnect(AbstractNioChannel.java:340)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:665)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:612)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:529)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:491)
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:905)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:832)
Caused by: java.net.ConnectException: Connection refused
```

After executing this command, we can see an error indicating the connection was refused.


## References
Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
