package org.se.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class ArticleServiceConnectorJSON implements ArticleServiceConnector
{
    private final Logger LOG = Logger.getLogger(ArticleServiceConnectorJSON.class);
    protected final Proxy PROXY;
    protected final String HOST;
    protected final String PORT;
    protected String BASIC_AUTHORIZATION_DATA;

    public ArticleServiceConnectorJSON()
    {
        LOG.info("ArticleServiceConnectorImpl()");

        Properties properties = new Properties();
        try
        {
            properties.load(this.getClass().getResourceAsStream("/rest.properties"));
            HOST = properties.getProperty("rest.host");
            PORT = properties.getProperty("rest.port");
            LOG.info("Connect to " + "https://" + HOST + ":" + PORT + "/articles");
            PROXY = Proxy.NO_PROXY;

            System.setProperty( "javax.net.ssl.trustStore", properties.getProperty("ssl.trustStore"));
            System.setProperty( "javax.net.ssl.trustStorePassword", properties.getProperty("ssl.trustStorePassword"));
            HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());

            String username = properties.getProperty("rest.username");
            String password = properties.getProperty("rest.password");
            String userPassword = username + ":" + password;
            BASIC_AUTHORIZATION_DATA = Base64.encodeBase64String(userPassword.getBytes());
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Can't setup remote connection!", e);
        }
    }


    @Override
    public void insert(Article article)
    {
        LOG.info("insert(" + article + ")");

        try
        {
            URL url = new URL("https://" + HOST + ":" + PORT + "/articles");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
            OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
            String json = convertArticle2Json(article);
            LOG.info("JSON: " + json);
            w.write(json);
            w.flush();
            w.close();

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 201)
                throw new ServiceException("Insert error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public void update(Article article)
    {
        LOG.info("update(" + article + ")");

        try
        {
            URL url = new URL("https://" + HOST + ":" + PORT + "/articles/" + article.getId());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
            OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
            w.write(convertArticle2Json(article));
            w.flush();
            w.close();

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 204)
                throw new ServiceException("Update error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward PUT request to the service!", e);
        }
    }


    @Override
    public void delete(int id)
    {
        LOG.info("delete(" + id + ")");
        try
        {
            URL url = new URL("https://" + HOST + ":" + PORT + "/articles/" + id);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
            connection.setRequestMethod("DELETE");

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 204)
                throw new ServiceException("Delete error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward DELETE request to the service!", e);
        }
    }


    @Override
    public Article findById(int id)
    {
        LOG.info("findById(" + id + ")");

        try
        {
            // Request
            URL url = new URL("https://" + HOST + ":" + PORT + "/articles/" + id);
            LOG.info("URL: " + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);

            // Response
            int httpResponseCode = connection.getResponseCode();
            LOG.info("HTTP response code: " + httpResponseCode);
            if (httpResponseCode != 200)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            String json = content.toString();
            LOG.info("JSON: " + json);
            Article article = convertJson2Article(json);
            return article;
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public List<Article> findAll()
    {
        try
        {
            // Request
            URL url = new URL("https://" + HOST + ":" + PORT + "/articles");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            String json = content.toString();
            List<Article> articles = convertJsonArray2ArticleList(json);
            return articles;
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }

    
    /*
     * JSON Serialization
     */
    
    protected Article convertJson2Article(String json)
    {
        Article article = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            article = mapper.readValue(json, Article.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return article;
    }
    
    protected List<Article> convertJsonArray2ArticleList(String json)
    {
        List<Article> list = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(json, new TypeReference<List<Article>>(){});
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return list;
    }
    
    protected String convertArticle2Json(Article article)
    {
        String json = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(article);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return json;
    }
}
