package org.se.lab;

import io.grpc.stub.StreamObserver;

public class SensorServiceImpl
    extends SensorServiceGrpc.SensorServiceImplBase
{
    @Override
    public void measure(Sensor sensor, StreamObserver<SensorData> responseObserver)
    {
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
}
