package org.se.lab;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;

public class GRPCClientTest
{
    @Test
    public void testHelloService()
    {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Homer")
                .setLastName("Simpson")
                .build());

        System.out.println("Response received from server:\n" + helloResponse.getGreeting());

        channel.shutdown();
    }

}
