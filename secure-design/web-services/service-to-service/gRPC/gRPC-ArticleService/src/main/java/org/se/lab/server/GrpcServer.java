package org.se.lab.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws Exception
    {
        Server server = ServerBuilder
                .forPort(8081)
                .addService(new ArticleServiceImpl()).build();

        server.start();
        LOGGER.info("gRPC Server started...");
        server.awaitTermination();
    }
}
