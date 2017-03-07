package org.se.lab;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginAttackTest 
	extends AbstractJdbcTest
{		
	@Before
	public void setup() throws ClassNotFoundException, SQLException  
	{
		executeSqlScript("sql/createUserTable.sql");
		executeSqlScript("sql/insertUserTable.sql");
	}
	
	@After
	public void teardown()
	{		        
        executeSqlScript("sql/dropUserTable.sql");
	}

	
	@Test
    public void testLogin() throws SQLException, 
        ClassNotFoundException
    {
        final String username = "homer";
        final String password = "dfa8327f5bfa4c672a04f9b38e348a70";
        
        LoginService service = new LoginService();
        boolean result = service.login(getConnection(), username, password);
        Assert.assertTrue(result);
    }

    @Test
    public void testSqlInjectionAttack1() throws SQLException, ClassNotFoundException
    {
        final String username = "homer' OR 1 --'"; // SQL injection
        final String password = "blah";
        
        LoginService service = new LoginService();
        boolean result = service.login(getConnection(), username, password);
        Assert.assertFalse(result);
    }

    @Test
    public void testSqlInjectionAttack2() throws SQLException, ClassNotFoundException
    {
        final String username = "homer' #"; // SQL injection
        final String password = "blah";
        
        LoginService service = new LoginService();
        boolean result = service.login(getConnection(), username, password);
        Assert.assertFalse(result);
    }

    @Test
    public void testSqlInjectionAttack3() throws SQLException, ClassNotFoundException
    {
        final String username = "' OR 1 #"; // SQL injection
        final String password = "blah";
        
        LoginService service = new LoginService();
        boolean result = service.login(getConnection(), username, password);
        Assert.assertFalse(result);
    }
}
