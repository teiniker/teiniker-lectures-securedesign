package org.se.lab;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class GRPCClientTest
{
    private ManagedChannel channel;
    private SensorServiceGrpc.SensorServiceBlockingStub service;

    @Before
    public void setUp()
    {
        channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

       service = SensorServiceGrpc.newBlockingStub(channel);
    }

    @After
    public void tearDown()
    {
        channel.shutdown();
    }

    @Test
    public void testServerSideStreaming()
    {
        Sensor sensor = Sensor.newBuilder().setType(SensorType.DHT11).build();
        Iterator<SensorData> dataList = service.measure(sensor);

        for(int i = 1; dataList.hasNext(); i++)
        {
            SensorData data = dataList.next();
            System.out.printf("Sensor Data %d: %s\n", i, data.toString());
        }
    }

}
