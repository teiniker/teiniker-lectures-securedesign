package org.se.lab.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAOImpl
{
    /*
     * Dependency: ---[1]-> connection:Connection
     */
    private Connection connection;
    protected final Connection getConnection()
    {
        return connection;
    }
    public final void setConnection(final Connection connection)
    {
        if (connection == null)
            throw new NullPointerException("connection");
        this.connection = connection;
    }

    
    protected void closeResultSet(ResultSet rs) 
        throws DAOException
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            } 
            catch (SQLException e)
            {
                throw new DAOException("result set closing failure");
            }
        }
    }

    
    protected void closePreparedStatement(PreparedStatement pstmt) 
        throws DAOException
    {
        if (pstmt != null)
        {
            try
            {
                pstmt.close();
            } 
            catch (SQLException e)
            {
                throw new DAOException("prepared statement closing failure");
            }
        }
    }

    protected void closeStatement(Statement stmt) 
        throws DAOException
    {
        if (stmt != null)
        {
            try
            {
                stmt.close();
            } 
            catch (SQLException e)
            {
                throw new DAOException("statement closing failure");
            }
        }
    }
}
