package org.se.lab;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceConnectorITCase
{
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	
	private UserServiceConnector service;
	
	@Before
	public void init()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/insertUserTable.sql");
	
		service = new UserServiceConnectorJSON();
	}
	
	@After
	public void destroy()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");		
	}
	
	
	@Test
	public void testInsert()
	{
	    User user = new User(0, "maggie", "AZ2wv9X4WVHLRuRFLpZChYwAQVU=");
	    service.insert(user);
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void testUpdate() throws IOException, JAXBException
	{
	    User user = new User(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg=");
	    service.update(user);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void testDelete()
	{
	    service.delete(2);
	}
	
	
	@Test
	public void testFindById() 
	{
	    User user = service.findById(3);
	    
	    Assert.assertNotNull(user);
	    Assert.assertEquals("bart", user.getUsername());
	    Assert.assertEquals("Ls4jKY8G2ftFdy/bHTgIaRjID0Q=", user.getPassword());
	}
	
	
	@Test
	public void testFindAll()
	{
	    List<User> users = service.findAll();
        
	    Assert.assertNotNull(users);
        Assert.assertEquals(4, users.size());
        System.out.println(users);
        Assert.assertEquals("homer", users.get(0).getUsername());
        Assert.assertEquals("marge", users.get(1).getUsername());
        Assert.assertEquals("bart", users.get(2).getUsername());
        Assert.assertEquals("lisa", users.get(3).getUsername());
	    
	}
}
