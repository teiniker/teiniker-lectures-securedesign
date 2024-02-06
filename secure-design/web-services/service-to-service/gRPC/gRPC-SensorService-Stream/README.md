# Example: Server-Side Streaming

The client sends a single request with a `Sensor` message and gets back several `SensorData` responses.

We add the `stream` keyword in front of the returned message type:
```
service SensorService
{
    rpc measure(Sensor) returns (stream SensorData);
}
```

On the **server-side**, for a single request we send back several responses to the client using the `onNext()`
method many times befor we call `onCompleted()`.

```Java
    @Override
    public void measure(Sensor sensor, StreamObserver<SensorData> responseObserver)
    {
        LOGGER.debug("measure(" + sensor + ")");

        for(int i=0; i<10; i++)
        {
            SensorData data = SensorData.newBuilder()
                .setSensorType(SensorType.DHT11)
                .setValue(20.0 + i)
                .setUnitOfMeasurement("°C")
                .build();
            responseObserver.onNext(data);
        }
        responseObserver.onCompleted();
    }
```

On the **client-side**, the server's remote method returns an `Iterator<>` of message instances which we
can use to iterate over the received data.

```Java
    @Test
    public void testDHT11Sensor()
    {
        Sensor sensor = Sensor.newBuilder().setType(SensorType.DHT11).build();
        Iterator<SensorData> dataList = service.measure(sensor);

        for(int i = 1; dataList.hasNext(); i++)
        {
            SensorData data = dataList.next();
            System.out.printf("Sensor Data %d: %s\n", i, data.toString());
        }
    }
```

Remember, an `Iterator<>` interface provides the methods `hasNext()` and `next()` which can be used to 
visit all elements of a given data structure.



## References

* [Streaming with gRPC in Java](https://www.baeldung.com/java-grpc-streaming)

* Kasun Indrasiri, Danesh Kuruppu. **gRPC: Up and Running**. O'Reilly .
    * Chapter 3: gRPC Communication Patterns

*Egon Teiniker, 2016-2022, GPL v3.0*
