package org.se.lab.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MessageSignature
{
    private static String keyProperty = System.getProperty("hmac.key");
    
    /*
     * init() can be used to override Wildfly system properties with test
     * specific values.
     */
    public static void init(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Invalid cipher key!");
        
        keyProperty = key;
    }
    
    
    public static String generateHMacString(String data) 
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
        catch (InvalidKeyException | UnsupportedEncodingException 
                | NoSuchAlgorithmException | DecoderException e)
        {
            throw new IllegalStateException("Can't calculate HMAC!", e);
        }
    }    
}
