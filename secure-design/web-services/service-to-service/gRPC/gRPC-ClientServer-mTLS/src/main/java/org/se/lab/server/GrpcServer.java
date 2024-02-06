package org.se.lab.server;

import io.grpc.Server;
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

import static io.grpc.netty.GrpcSslContexts.configure;

public class GrpcServer
{
    private static final Logger LOG = Logger.getLogger(GrpcServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException
    {
        SslContextBuilder sslClientContextBuilder = SslContextBuilder.forServer(new File("/tmp/sslcert/server.crt"),
                new File("/tmp/sslcert/server.pem"))
                .trustManager(new File("/tmp/sslcert/ca.crt"))
                .clientAuth(ClientAuth.REQUIRE);

        SslContext sslContext = GrpcSslContexts.configure(sslClientContextBuilder, SslProvider.OPENSSL).build();

        Server server = NettyServerBuilder.forAddress(new InetSocketAddress("localhost",8443))
                .addService(new HelloServiceImpl())
                .sslContext(sslContext)
                .build()
                .start();
        LOG.info("Server started...");

        server.awaitTermination();
    }
}
