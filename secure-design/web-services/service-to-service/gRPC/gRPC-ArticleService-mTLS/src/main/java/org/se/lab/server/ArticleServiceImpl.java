package org.se.lab.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.se.lab.*;
import org.se.lab.data.ArticleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleServiceImpl extends ArticleServiceGrpc.ArticleServiceImplBase
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private Map<Integer, ArticleEntity> articles = new HashMap<>();

    @Override
    public void insertArticle(Article request, StreamObserver<InsertReply> responseObserver)
    {
        LOGGER.debug("insertArticle");
        int id = generateId();

        ArticleEntity article = new ArticleEntity(id, request.getDescription(), request.getPrice());
        articles.put(id, article);

        InsertReply response = InsertReply.newBuilder()
                .setId(id)
                .setMessage(String.format("Article with id=%d successfully inserted.", id))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateArticle(Article request, StreamObserver<UpdateReply> responseObserver)
    {
        LOGGER.debug("updateArticle");
        int id = request.getId();
        ArticleEntity article = articles.get(id);

        if (article == null)
        {
            String message = String.format("Article with id=%d does not exist.", id);
            responseObserver.onError(Status.NOT_FOUND.withDescription(message).asRuntimeException());
            return;
        }

        article.setDescription(request.getDescription());
        article.setPrice(request.getPrice());

        UpdateReply response = UpdateReply.newBuilder()
                .setId(id)
                .setMessage(String.format("Article with id=%d successfully updated.", id))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteArticle(DeleteRequest request, StreamObserver<DeleteReply> responseObserver)
    {
        LOGGER.debug("deleteArticle");
        int id = request.getArticleId();
        ArticleEntity article = articles.remove(id);
        if (article == null)
        {
            String message = String.format("Article with id=%d does not exist.", id);
            responseObserver.onError(Status.NOT_FOUND.withDescription(message).asRuntimeException());
            return;
        }

        DeleteReply response = DeleteReply.newBuilder()
                .setMessage(String.format("Article with id=%d successfully deleted.", id))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findAllArticles(ArticleListRequest request, StreamObserver<ArticleListReply> responseObserver)
    {
        LOGGER.debug("findAllArticles");
        List<Article> articleTransferObjects = articles.values().stream().map(this::toArticleTransferObject).collect(Collectors.toList());
        ArticleListReply response = ArticleListReply.newBuilder().addAllArticles(articleTransferObjects).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Article toArticleTransferObject(ArticleEntity articleEntity)
    {
        return Article.newBuilder().setId(articleEntity.getId()).setDescription(articleEntity.getDescription()).setPrice(articleEntity.getPrice()).build();
    }

    @Override
    public void findArticleById(ArticleRequest request, StreamObserver<Article> responseObserver)
    {
        LOGGER.debug("findArticleById");
        int id = request.getArticleId();
        ArticleEntity article = articles.get(id);
        if (article == null)
        {
            String message = String.format("Article with id=%d does not exist.", id);
            responseObserver.onError(Status.NOT_FOUND.withDescription(message).asRuntimeException());
            return;
        }
        Article response = toArticleTransferObject(article);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private int generateId()
    {
        int id;
        SecureRandom random = new SecureRandom();
        do
        {
            id = random.nextInt();
        } while (id <= 0 || articles.containsKey(id));
        LOGGER.info("generated id {}", id);
        return id;
    }
}
