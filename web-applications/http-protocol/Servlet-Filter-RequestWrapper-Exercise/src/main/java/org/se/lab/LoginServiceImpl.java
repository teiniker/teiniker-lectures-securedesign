package org.se.lab;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LoginServiceImpl implements LoginService
{
	/*
	 * Simulate a database table
	 */
	private ConcurrentMap<String,String> table = new ConcurrentHashMap<String,String>();
	
	public LoginServiceImpl()
	{
		table.put("student", "JkyMOBvxbJgqTlmw3UxveAjFGgX2TDXbQsx4oqcodbs=");
		table.put("homer", "Kqq3lbODaQT4LvxsoihdknrtdSBiFOHaODQY65DJBS8=");
	}
	
	
	@Override
	public boolean login(String username, String password, String usergroup)
	{
		if(table.containsKey(username))
		{
			if(table.get(username).equals(password))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
