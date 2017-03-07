package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService
{
	private static final Map<String,String> table = new HashMap<String, String>();
		
	public enum LoginState {UNKNOWN_USER, LOCKED_USER, LOGIN_FAILED, LOGIN_SUCCESSFUL};
	
	static
	{
		table.put("homer", "1dafc8fddbd1b5f7fef5cb9c2f75a8265f095828301d7de285f94a1474d316dc3a466d1577dc4462e61e96143d8f2d392c572ae456d0608057457be90ea0691e");
		table.put("lisa" , "f13b7762550dd9df698fbd960475185551ef43c605cdbac9a3af401ddef35b52507fd95ddf09c5edfb7782ef8c3420ea274774d31f1aff6ff92c0c1731964252");
		table.put("bart" , "2e73ef3a8994418d04fcbbcafea0d5c1890b2fb693cf7c98eacf926fb7046500b428260acbc3c01f368cdb4c85bf6790b1ec75b635dc5663279bc1d86433f01f");
	}
	
	
	public synchronized LoginState login(String username, String password)
	{
		if(!isValidUsername(username))
			return LoginState.LOGIN_FAILED;
		
		if(!isValidPassword(password))
		{
			return LoginState.LOGIN_FAILED;
		}

		if(table.containsKey(username))
		{
			if(table.get(username).equals(hashValue(password)))
			{
				return LoginState.LOGIN_SUCCESSFUL;
			}
			else
			{
				return LoginState.LOGIN_FAILED;
			}
		}
		else
		{
			return LoginState.UNKNOWN_USER;
		}
	}

	
	/*
	 * Helper methods
	 */
	
	private final static String NAME_REGEX = "^[a-zA-Z0-9_]{4,64}$";	
	private final Pattern namePattern = Pattern.compile(NAME_REGEX);
	protected boolean isValidUsername(String username)
	{
		if(username == null)
			return false;
		
		username = Normalizer.normalize(username, Form.NFKC);
		Matcher m = namePattern.matcher(username);		
		if(!m.matches())		
			return false;
		else
			return true;
	}

	
	private final static String PASSWORD_REGEX = "^[a-zA-Z0-9_!$%&?]{4,}$";	
	private final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
	protected boolean isValidPassword(String password)
	{
		if(password == null)
			return false;
		
		password = Normalizer.normalize(password, Form.NFKC);
		Matcher m = passwordPattern.matcher(password);		
		if(!m.matches())		
			return false;
		else
			return true;
	}
	
	
	private String hashValue(String s)
	{
		MessageDigest algorithm;
		try
		{
			algorithm = MessageDigest.getInstance("SHA-512");
			byte[] defaultBytes = s.getBytes("UTF-8");
			algorithm.update(defaultBytes);
			byte[] bytes = algorithm.digest();
			return convertToHexString(bytes);
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return s;
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return s;
		}		
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