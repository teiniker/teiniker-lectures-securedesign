package org.se.lab.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOMySQLImpl
    extends AbstractDAOImpl
    implements UserDAO
{
	/*
	 * DAO operations
	 */


	public boolean isValidUser(String username, String password)
	{
		final String SQL = "SELECT id FROM user WHERE username ='" + username
				+ "' AND password = '" + password + "'";

		Statement stmt = null;
		ResultSet rs = null;
		boolean result = false;
		try
		{
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(SQL);
			result = rs.next();
		}
		catch (SQLException e)
		{
			return false;
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(stmt);
		}
		return result;
	}
}
