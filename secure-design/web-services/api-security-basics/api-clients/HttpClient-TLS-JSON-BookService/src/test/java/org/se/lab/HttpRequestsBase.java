package org.se.lab;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpRequestsBase
{
    // Replacement for system property setting on the command line
    // -Djavax.net.ssl.trustStore=...
    // -Djavax.net.ssl.trustStorePassword=...
    SSLContext sslContextFromTruststore(String trustStorePath, String trustStorePassword)
            throws Exception
    {
        KeyStore ts = KeyStore.getInstance("PKCS12");
        try (InputStream in = Files.newInputStream(Path.of(trustStorePath)))
        {
            ts.load(in, trustStorePassword.toCharArray());
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);
        SSLContext ctx = SSLContext.getInstance("TLS"); // negotiates best supported TLS version
        ctx.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
        return ctx;
    }

    /*
     * JSON Serialization
     */
    
    protected Book convertJson2Book(String json)
    {
        Book book = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            book = mapper.readValue(json, Book.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return book;
    }
    
    protected List<Book> convertJsonArray2BookList(String json)
    {
        List<Book> list = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(json, new TypeReference<List<Book>>(){});
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return list;
    }
    
    protected String convertBook2Json(Book book)
    {
        String json = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(book);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return json;
    }
}