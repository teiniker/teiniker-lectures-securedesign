package org.se.lab.service;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceXMLTest extends AbstractTestBase
{
    private static final String WEB_APP_NAME = "/REST-EJB-UserService-BasicAuth/v1";
    private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();

    @Before
    public void init()
    {
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/insertUserTable.sql");
    }

    @After
    public void destroy()
    {
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");
    }

            
    @Test
    public void testHttpClient() throws ClientProtocolException, IOException
    {
        HttpHost target = new HttpHost("localhost", 8080, "http");
     
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("student", "student"));
        
//        HttpHost proxy = new HttpHost("localhost", 8010);
//        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        
        CloseableHttpClient httpclient = HttpClients.custom()
  //              .setRoutePlanner(routePlanner)
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        // Add AuthCache to the execution context
        HttpClientContext localContext = HttpClientContext.create();

        // Begin preemptive authentication
        // End preemptive authentication

        HttpGet httpget = new HttpGet("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");

        System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);
        CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
 
        int httpResponseCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(200, httpResponseCode);
        
        String content = EntityUtils.toString(response.getEntity());
        final String EXPECTED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<user id=\"3\">"
                + "<username>bart</username>" + "<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>" + "</user>";
        System.out.println("Response-Content:\n" + content);
        Assert.assertEquals(EXPECTED, content);
        
        response.close();
        httpclient.close();
    }

    // TODO: insert

    // TODO: update
    
    // TODO: delete
    
    // TODO: findAll    
}
