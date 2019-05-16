package org.se.lab.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;


public class HMACTest
{
    private String keyProperty = "7cb46d33ae687fa8c98a712cfa7c0f98836405138040a9e03260d03ee8973885e6d800adcccd6dfa2961ea714f2ca3f8a1b1838cc6f86298f93d77368bae8d65";
    
	@Test
	public void testHMac() 
		throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, DecoderException
	{
	    //Key hmacKey = KeyGenerator.getInstance("HmacSHA1").generateKey();
	    byte[] keyBytes = Hex.decodeHex(keyProperty.toCharArray());
	    Key key = new SecretKeySpec(keyBytes, "HmacSHA1");
	    
	    String message = "Hello KBerg!"; 		
		byte[] inputBytes = message.getBytes("UTF-8");
		System.out.println("inputBytes: " + Hex.encodeHexString(inputBytes));
		
		Mac hmac = Mac.getInstance("HmacSHA1");		
		hmac.init(key);
		hmac.update(inputBytes);
		byte[] macBytes = hmac.doFinal();

		System.out.println("macBytes: " + Hex.encodeHexString(macBytes));
	}

	
	@Test
	public void testGenerateHMacString()
	{
	    
	    String data = "Hello KBerg!";
	    
	    String hmac = generateMacString(data);
	    
	    System.out.println("hmac: " + hmac);
	}
	
	
	public String generateMacString(String data) 
	{
	    try
	    {
	        byte[] keyBytes = Hex.decodeHex(keyProperty.toCharArray());
    	    Key key = new SecretKeySpec(keyBytes, "HmacSHA1");
    	    byte[] dataBytes = data.getBytes("UTF-8");
    	    Mac hmac = Mac.getInstance("HmacSHA1");        
            hmac.init(key);
            hmac.update(dataBytes);
            byte[] macBytes = hmac.doFinal();
            return Hex.encodeHexString(macBytes);
	    }
	    catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | DecoderException e)
	    {
	        throw new IllegalStateException("Can't calculate HMAC!", e);
	    }
	}
}
