package org.se.lab;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HttpsRestRequestTest extends HttpsRestTestBase
{
    private final String WEB_APP_NAME = "/REST-EJB-UserService-TLS/v1";
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
    public void testFindById() throws IOException, JAXBException
    {
        URL url = new URL("https://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");

        String content = httpsGetRequest(url);
        final String EXPECTED = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + 
                    "<user id=\"3\">" + 
                        "<username>bart</username>" + 
                        "<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>" + 
                    "</user>\n";

        Assert.assertEquals(EXPECTED, content);
    }

    @Test
    public void testFindAll() throws IOException, JAXBException
    {
        // Request
        URL url = new URL("https://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
        String content = httpsGetRequest(url);

        final String EXPECTED = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<collection>"
                + "<user id=\"1\">" + "<username>homer</username>" + "<password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>" + "</user>"
                + "<user id=\"2\">" + "<username>marge</username>" + "<password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password>" + "</user>"
                + "<user id=\"3\">" + "<username>bart</username>" + "<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>" + "</user>"
                + "<user id=\"4\">" + "<username>lisa</username>" + "<password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password>" + "</user>"
                + "</collection>\n";
        Assert.assertEquals(EXPECTED, content);
    }
}
