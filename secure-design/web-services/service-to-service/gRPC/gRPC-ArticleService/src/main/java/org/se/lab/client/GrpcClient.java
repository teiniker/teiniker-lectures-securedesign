package org.se.lab.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.se.lab.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args)
    {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        ArticleServiceGrpc.ArticleServiceBlockingStub stub
                = ArticleServiceGrpc.newBlockingStub(channel);

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

        /*
        LOGGER.info("delete non existing article 1 again");
        try
        {
            stub.deleteArticle(DeleteRequest.newBuilder().setArticleId(insertReply1.getId()).build());
            LOGGER.error("deleted non existing article {}!", insertReply1.getId());
        }
        catch (StatusRuntimeException e)
        {
            LOGGER.debug("reply: {}", e.toString(), e);
        }
*/
        channel.shutdown();
    }
}
