package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		table.put("student", "cd73502828457d15655bbd7a63fb0bc8");
		table.put("homer", "54a8723466e5d487247f3d93d51c66bc");
		table.put("marge", "1a87d0eab5010e526072a0240d731f27");
		table.put("bart", "a0e4bf66061c51a0ff98056899f6651a");
		table.put("lisa", "01187d6d3bff73b7f5e7da29064bab1b");
		table.put("maggie", "b9dc6731ba225e3cd0763b28bb7560b3");
	}
	
	
	@Override
	public boolean login(String username, String password, String usergroup)
	{
		if(table.containsKey(username))
		{
			try
			{
				if(table.get(username).equals(calculateMd5String(password)))
				{
					return true;
				}
				else
				{
					return false;
				}
			} 
			catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	
	
	private String calculateMd5String(final String message)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest algorithm = MessageDigest.getInstance("MD5");		
		algorithm.update(message.getBytes("UTF-8"));
		byte[] bytes = algorithm.digest();
		return convertToHexString(bytes);
	}

	
	private String convertToHexString(byte[] bytes)
	{
		StringBuilder hex = new StringBuilder();
		for(byte b : bytes)
		{
			int i = (b & 0xff); // copy the byte bit pattern into int value
			hex.append(String.format("%02x", i));
		}
		return hex.toString();
	}
}
