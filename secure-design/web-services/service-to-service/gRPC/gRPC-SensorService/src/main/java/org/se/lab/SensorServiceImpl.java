package org.se.lab;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SensorServiceImpl
    extends SensorServiceGrpc.SensorServiceImplBase
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorServiceImpl.class);

    @Override
    public void measure(Sensor sensor, StreamObserver<SensorData> responseObserver)
    {
        LOGGER.debug("measure(" + sensor + ")");

        SensorData data;
        switch(sensor.getType())
        {
            case DHT11 -> data = SensorData.newBuilder()
                    .setSensorType(SensorType.DHT11)
                    .setValue(23.7)
                    .setUnitOfMeasurement("°C")
                    .build();

            case DHT22 -> data = SensorData.newBuilder()
                    .setSensorType(SensorType.DHT22)
                    .setValue(61.2)
                    .setUnitOfMeasurement("%")
                    .build();

            case HC_SR04 -> data = SensorData.newBuilder()
                    .setSensorType(SensorType.HC_SR04)
                    .setValue(24.5)
                    .setUnitOfMeasurement("cm")
                    .build();

            default -> data = SensorData.newBuilder().build();
        }
        responseObserver.onNext(data);
        responseObserver.onCompleted();
    }
}
