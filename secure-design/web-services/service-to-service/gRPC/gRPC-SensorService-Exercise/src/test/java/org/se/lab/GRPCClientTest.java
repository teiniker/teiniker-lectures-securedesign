package org.se.lab;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GRPCClientTest
{
    private ManagedChannel channel;
    private SensorServiceGrpc.SensorServiceBlockingStub service;

    @Before
    public void setUp()
    {
        // TODO: C, D
    }

    @After
    public void tearDown()
    {
        // TODO: C, D
    }

    @Test
    public void testDHT11Sensor()
    {
        Sensor sensor = Sensor.newBuilder().setType(SensorType.DHT11).build();
        SensorData data = service.measure(sensor);

        System.out.println("Sensor Data:\n" + data);
        Assert.assertEquals(SensorType.DHT11, data.getSensorType());
        Assert.assertEquals("°C", data.getUnitOfMeasurement());
        Assert.assertEquals(23.7,data.getValue(), 1E-3);
    }

    @Test
    public void testDHT22Sensor()
    {
        Sensor sensor = Sensor.newBuilder().setType(SensorType.DHT22).build();
        SensorData data = service.measure(sensor);

        System.out.println("Sensor Data:\n" + data);
        Assert.assertEquals(SensorType.DHT22, data.getSensorType());
        Assert.assertEquals("%", data.getUnitOfMeasurement());
        Assert.assertEquals(61.2,data.getValue(), 1E-3);
    }

    @Test
    public void testHC_SR04Sensor()
    {
        Sensor sensor = Sensor.newBuilder().setType(SensorType.HC_SR04).build();
        SensorData data = service.measure(sensor);

        System.out.println("Sensor Data:\n" + data);
        Assert.assertEquals(SensorType.HC_SR04, data.getSensorType());
        Assert.assertEquals("cm", data.getUnitOfMeasurement());
        Assert.assertEquals(24.5,data.getValue(), 1E-3);
    }
}
