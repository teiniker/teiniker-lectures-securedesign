package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

public class SHATest
{
    @Test
    public void testSHA1() 
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String message = "mypassword"; 
        
        MessageDigest algorithm = MessageDigest.getInstance("SHA-1");       
        algorithm.update(message.getBytes("UTF-8"));
        byte[] bytes = algorithm.digest();

        String hexString = Hex.encodeHexString(bytes);      
        Assert.assertEquals(40, hexString.length()); 
        Assert.assertEquals("91dfd9ddb4198affc5c194cd8ce6d338fde470e2", hexString);
        
        String base64String = Base64.encodeBase64String(bytes);
        Assert.assertEquals(28, base64String.length()); 
        Assert.assertEquals("kd/Z3bQZiv/FwZTNjObTOP3kcOI=", base64String);
    }
}
