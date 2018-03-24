package org.se.lab;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class SecureRandomTest
{
	@Test
	public void testNextBytes() throws NoSuchAlgorithmException
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		
		byte bytes[] = new byte[20];
	    random.nextBytes(bytes);
		
	    String hexString = Hex.encodeHexString(bytes);		    
	    System.out.println(hexString);
	}
}
