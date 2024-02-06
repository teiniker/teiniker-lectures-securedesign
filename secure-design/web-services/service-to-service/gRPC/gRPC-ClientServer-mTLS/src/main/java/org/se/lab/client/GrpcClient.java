package org.se.lab.client;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.se.lab.*;

public class GrpcClient
{
    private static final Logger LOG = Logger.getLogger(GrpcClient.class.getName());

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
}
