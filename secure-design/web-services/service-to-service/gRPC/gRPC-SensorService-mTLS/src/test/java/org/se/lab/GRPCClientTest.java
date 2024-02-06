package org.se.lab;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GRPCClientTest
{
    private ManagedChannel channel;
    private SensorServiceGrpc.SensorServiceBlockingStub service;

    @Before
    public void setUp() throws SSLException
    {
        SslContext sslContext = GrpcSslContexts.forClient()
                .trustManager(new File("/tmp/sslcert/ca.crt"))
                .keyManager(new File("/tmp/sslcert/client.crt"), new File("/tmp/sslcert/client.pem"))
                .build();

        channel = NettyChannelBuilder.forAddress("localhost", 8443)
                .negotiationType(NegotiationType.TLS)
                .sslContext(sslContext)
                .build();

       service = SensorServiceGrpc.newBlockingStub(channel);
    }

    @After
    public void tearDown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
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
