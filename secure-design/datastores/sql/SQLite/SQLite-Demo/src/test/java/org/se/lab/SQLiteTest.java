package org.se.lab;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SQLiteTest 
{		
    private static final String DB_FILE = "test.db";
    private static final String CONNECTION_URL = "jdbc:sqlite:" + DB_FILE;

    /**
     * Setup: Creates a fresh SQLite DB file and populates it with data 
     * so we have something to read during the test.
     */
    @Before
    public void setUp() throws SQLException 
    {
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        Statement stmt = conn.createStatement();

        // Create a table
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, role TEXT)");
        // Clear old data just in case
        stmt.execute("DELETE FROM users");
        // Insert sample data
        stmt.execute("INSERT INTO users (id, name, role) VALUES (1, 'homer', 'USER')");
        stmt.execute("INSERT INTO users (id, name, role) VALUES (2, 'burns', 'ADMIN')");

        stmt.close();
        conn.close();
    }

    @After 
    public void teardown() throws SQLException 
    {
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        Statement stmt = conn.createStatement();

        stmt.execute("DROP TABLE IF EXISTS users");
        
        stmt.close();
        conn.close();
    }


    @Test
    public void testReadSqliteFile() throws SQLException
    {
        String query = "SELECT id, name, role FROM users WHERE id = ?";
        String foundName = null;
        String foundRole = null;

        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, 1);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) 
        {
            foundName = rs.getString("name");
            foundRole = rs.getString("role");
        }
        pstmt.close();
        conn.close();
 
        Assert.assertEquals("homer", foundName);
        Assert.assertEquals("USER", foundRole);
    }
}
