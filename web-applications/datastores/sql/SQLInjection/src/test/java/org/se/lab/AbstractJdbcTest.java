package org.se.lab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

// TODO: add explicit transaction handling for a rollback teardown

class AbstractJdbcTest 
{
	private final JdbcHelper JDBC_HELPER = new JdbcHelper();
	
	
	/**
	 * Execute a SQL script file.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected void executeSqlScript(String sqlFile) 
	{
		JDBC_HELPER.executeSqlScript(sqlFile);
	}
	
	protected Connection getConnection() throws ClassNotFoundException, SQLException
	{
	    return JDBC_HELPER.getConnection();
	}
	
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
