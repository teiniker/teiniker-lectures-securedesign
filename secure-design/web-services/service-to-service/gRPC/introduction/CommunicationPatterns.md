# gRPC Communication Patterns

With gRPC, we can leverage different inter-process communication patterns (or RPC styles) other than the 
simple request–response pattern. gRPC defines four **kinds of RPC styles**:

* **Unary RPCs** where the client sends a single request to the server and gets a single response back,
  just like a normal function call.

  _Example_: `rpc SayHello(HelloRequest) returns (HelloResponse);`

* **Server-side streaming RPCs** where the client sends a request to the server and gets a stream to read a sequence
  of messages back. The client reads from the returned stream until there are no more messages.
  gRPC guarantees message ordering within an individual RPC call.

  _Example_: [Server-side Streaming - SensorService](../gRPC-SensorService-Stream)`

* **Client-side streaming RPCs** where the client writes a sequence of messages and sends them to the server,
  again using a provided stream.
  Once the client has finished writing the messages, it waits for the server to read them and return its response.
  Again gRPC guarantees message ordering within an individual RPC call.

  _Example_: `rpc lotsOfGreetings(stream HelloRequest) returns (HelloResponse);`

* **Bidirectional streaming RPCs** where both sides send a sequence of messages using a read-write stream.
  The two streams operate independently, so clients and servers can read and write in whatever order they like:
  for example, the server could wait to receive all the client messages before writing its responses,
  or it could alternately read a message then write a message, or some other combination of reads and writes.
  The order of messages in each stream is preserved.

  _Example_: `rpc bidiHello(stream HelloRequest) returns (stream HelloResponse);`

Simple RPC is the most basic one; it is pretty much a simple request–response style remote procedure invocation. 
Server-streaming RPC allows you to send multiple messages from the service to the consumer after the first invocation 
of the remote method, while client streaming allows you to send multiple messages from the client to the service. 


## References

* Kasun Indrasiri, Danesh Kuruppu. **gRPC: Up and Running**. O'Reilly .
    * Chapter 3. gRPC Communication Patterns

*Egon Teiniker, 2016-2022, GPL v3.0*
