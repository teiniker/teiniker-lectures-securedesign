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

        // TODO: B
    }
}
