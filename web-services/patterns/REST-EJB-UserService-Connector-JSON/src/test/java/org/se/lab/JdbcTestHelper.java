package org.se.lab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


class JdbcTestHelper 
{
	public static final String SQL_STATEMENT_DELIMITER = ";";
	
	private String driver;
    private String url;
    private String user;
    private String password;  
	
    
    /*
     * Constructors
     */
    
    public JdbcTestHelper()
    {
    	this("/jdbc.properties");
    }
    
    public JdbcTestHelper(String propertyFileName) 
    {
    	if(propertyFileName == null || propertyFileName.length() == 0)
    		throw new IllegalArgumentException("Invalid property file name!");
    	    	
        try
		{
        	Properties jdbcProperties = new Properties();
			jdbcProperties.load(this.getClass().getResourceAsStream(propertyFileName));
			driver = jdbcProperties.getProperty("jdbc.driver");
			url = jdbcProperties.getProperty("jdbc.url");
			user = jdbcProperties.getProperty("jdbc.username");
			password = jdbcProperties.getProperty("jdbc.password");  
		}
		catch(Exception e)
		{
			throw new IllegalStateException("Unable to load " + propertyFileName + "!");
		}
    }
    

    /*
     * Public Methods
     */
    
    
    /**
     * Read a SQL script and execute each SQL statement.
     * 
     * @param sqlScriptFileName
     */
    public void executeSqlScript(String sqlScriptFileName)
	{
    	if(sqlScriptFileName == null || sqlScriptFileName.length() == 0)
    		throw new IllegalArgumentException("Invalid SQL script file name!");

		try
		{			
			String sqlScript = loadSqlScript(sqlScriptFileName);
			String[] sqlStatements = sqlScript.split(SQL_STATEMENT_DELIMITER);		
			executeSqlStatements(sqlStatements);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Can't execute SQL script: " + e.getMessage());
		}
	}

	    
    /**
     * Executes a single SQL statement given by a string parameter.
     * 
     * @param sqlStatement
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void executeSqlStatement(String sqlStatement) 
    	throws ClassNotFoundException, SQLException, FileNotFoundException, IOException
    {
    	if(sqlStatement == null || sqlStatement.length() == 0)
    		throw new IllegalArgumentException("Empty sqlStatement!");
    	
    	Connection con = null;
    	Statement st = null;
    	try
    	{
    		con = getConnection();
    		st = con.createStatement();            
    		st.execute(sqlStatement);            
    	}
    	finally
    	{
    		if(st != null) 
    			st.close();
    		if(con != null)
    			con.close();
    	}
    }
    
    
    /**
     * Executes a sequence of SQL statements given by a string array parameter.
     * 
     * @param sqlStatements
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void executeSqlStatements(String[] sqlStatements) 
    	throws SQLException, FileNotFoundException, IOException, ClassNotFoundException 
    {
    	if(sqlStatements == null)
    		throw new IllegalArgumentException("Empty sqlStatement array!");
    	
    	Connection con = null;
    	Statement st = null;
    	try
    	{
    		con = getConnection();
    		con.setAutoCommit(false);
    		st = con.createStatement();            
    		for(String sqlStatement : sqlStatements)
    		{
    			System.out.println("SQL> " + sqlStatement);
    			st.execute(sqlStatement);
    		}
    		con.commit();
    	}
    	finally
    	{
    		if(st != null) 
    			st.close();
    		if(con != null) 
    		{
    			con.rollback();
    			con.close();
    		}
    	}
    }

    
    
    /*
     * Utility Methods
     */
    
    
    /**
     * Load an SQL script and eliminate comment lines.
     * 
     * @param sqlScriptFileName
     * @return
     * @throws IOException
     */
    protected String loadSqlScript(String sqlScriptFileName) 
    throws IOException
    {
    	if(sqlScriptFileName == null || sqlScriptFileName.length() == 0)
    		throw new IllegalArgumentException("Invalid SQL script file name!");
    	
    	BufferedReader in = new BufferedReader(new FileReader(sqlScriptFileName));
    	StringBuilder buffer = new StringBuilder();
    	String line;
    	while((line = in.readLine()) != null)
    	{
    		if(isCommentLine(line))
    			continue;
    		buffer.append(line.trim());
    	}
    	in.close();
    	return buffer.toString();    
    }
    
    
    /**
     * Check if a given line is a SQL comment.
     * 
     * @param line
     * @return
     */
    protected boolean isCommentLine(String line)
    {
    	if(line == null || line.length() == 0)
    		return false;
    	
    	String comment = line.trim(); 
    	if(comment.startsWith("--") || comment.startsWith("//"))
    		return true;
    	else
    		return false;    	
    }
    
    
    
    /**
     * Establish a JDBC connection to a database as defined by the driver, 
     * url, user and password fields.
     *   
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected Connection getConnection() 
    	throws ClassNotFoundException, SQLException 
    {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

    
    /*
     * Tx handling methods
     */
	
    protected void txBegin(Connection c) throws SQLException
    {
        c.setAutoCommit(false);
    }

    protected void txCommit(Connection c) throws SQLException
    {
        c.commit();
        c.setAutoCommit(true);
    }

    protected void txRollback(Connection c) throws SQLException
    {
        c.rollback();
        c.setAutoCommit(true);
    }

}
