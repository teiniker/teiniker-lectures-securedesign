package org.se.lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

import org.junit.Assert;
import org.junit.Test;


public class BruteForceAttackTest
	extends AbstractHttpClientTest
{
	@Test
	public void testCalculateHashString()
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		final String passwd = "student";

		String hash = calculateHashString(passwd);
		Assert.assertEquals("264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb", hash);
	}

	
	@Test
	public void testBruteForceAttack() throws NoSuchAlgorithmException, IOException
	{
		final String filename = "wordlist.txt";
		final String username = "homer"; 	
		
		// TODO: Login as homer

		URL url = new URL("http://localhost:8080" +
					"/Servlet-SimpleLogin/controller" +
					"?username=" + username + "&password=" + password + "&usergroup=User&action=Login");			
	
	
	}

	
	/*
	 * Helper methods
	 */

	private String calculateHashString(final String message)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		algorithm.update(message.getBytes("UTF-8"));
		byte[] bytes = algorithm.digest();
		return Hex.encodeHexString(bytes);
	}
}
