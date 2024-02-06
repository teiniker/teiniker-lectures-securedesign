package org.se.lab.client;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.se.lab.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class GrpcClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args) throws Exception
    {
        SslContext context = GrpcSslContexts.forClient()
                .trustManager(new File("/tmp/sslcert/ca.crt"))
                .keyManager(new File("/tmp/sslcert/client.crt"), new File("/tmp/sslcert/client.pem"))
                .build();

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 8443)
            .negotiationType(NegotiationType.TLS)
            .sslContext(context)
            .build();

        ArticleServiceGrpc.ArticleServiceBlockingStub stub = ArticleServiceGrpc.newBlockingStub(channel);

        // Use the remote ArticleService

        LOGGER.info("insert article 1");
        InsertReply insertReply1 = stub.insertArticle(Article.newBuilder()
                .setDescription("Test Article 1")
                .setPrice(123)
                .build());
        LOGGER.debug("reply: {}", insertReply1.getMessage());

        LOGGER.info("insert article 2");
        InsertReply insertReply2 = stub.insertArticle(Article.newBuilder()
                .setDescription("Test Article 2")
                .setPrice(456)
                .build());
        LOGGER.debug("reply: {}", insertReply2.getMessage());

        LOGGER.info("find all");
        ArticleListReply listReply1 = stub.findAllArticles(ArticleListRequest.newBuilder().build());
        System.out.println("reply: " + listReply1.toString());

        LOGGER.info("update article 2");
        UpdateReply updateReply = stub.updateArticle(Article.newBuilder().setId(insertReply2.getId()).setDescription("Test Article 2 with new description and price").setPrice(457).build());
        LOGGER.debug("reply: {}", updateReply.getMessage());

        LOGGER.info("findArticleById article 2");
        Article articleById2 = stub.findArticleById(ArticleRequest.newBuilder().setArticleId(insertReply2.getId()).build());
        LOGGER.debug("reply: {}", articleById2);

        LOGGER.info("delete article 1");
        DeleteReply deleteReply1 = stub.deleteArticle(DeleteRequest.newBuilder().setArticleId(insertReply1.getId()).build());
        LOGGER.debug("reply: {}", deleteReply1.getMessage());

        LOGGER.info("find all 2");
        ArticleListReply listReply2 = stub.findAllArticles(ArticleListRequest.newBuilder().build());
        LOGGER.debug("reply: {}", listReply2.toString());

        //channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
