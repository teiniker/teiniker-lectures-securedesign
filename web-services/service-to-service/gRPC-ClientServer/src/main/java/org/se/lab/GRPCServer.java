package org.se.lab;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class GRPCServer
{
    private static final Logger LOG = Logger.getLogger(GRPCServer.class.getName());

    final static int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Server server = ServerBuilder
                .forPort(PORT)
                .addService(new HelloServiceImpl()).build();

        server.start();
        LOG.info("Server started, listening on port " + PORT );

        server.awaitTermination();
    }
}
