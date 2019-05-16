package org.se.lab.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MessageEncryption
{
    private static String keyProperty = System.getProperty("encryption.key");
    private static String ivProperty = System.getProperty("encryption.iv");
    
    /*
     * init() can be used to override Wildfly system properties with test
     * specific values.
     */
    public static void init(String key, String iv)
    {
        if(key == null)
            throw new IllegalArgumentException("Invalid cipher key!");
        
        if(iv == null)
            throw new IllegalArgumentException("Invalid cipher IV!");
        
        keyProperty = key;
        ivProperty = iv;
    }
    
    
    public static String encryptToString(String inputString)
    {
        try
        {   
//            String keyProperty = System.getProperty("encryption.key");
//            String ivProperty = System.getProperty("encryption.iv");
            
            byte[] input = inputString.getBytes();
            byte[] ivBytes = Hex.decodeHex(ivProperty.toCharArray());
            byte[] keyBytes = Hex.decodeHex(keyProperty.toCharArray());
            
            // Setup
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
            int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
            return Hex.encodeHexString(cipherText);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException 
                | InvalidKeyException | InvalidAlgorithmParameterException 
                | ShortBufferException | IllegalBlockSizeException 
                | BadPaddingException | DecoderException e)
        {
            throw new IllegalStateException("Can't encrypt input!", e);
        }
    }
    
    
    public static String decryptToString(String cipherText)
    {
        try
        {
//            String keyProperty = System.getProperty("encryption.key");
//            String ivProperty = System.getProperty("encryption.iv");
            
            byte[] ivBytes = Hex.decodeHex(ivProperty.toCharArray());
            byte[] keyBytes = Hex.decodeHex(keyProperty.toCharArray());
            byte[] cipherBytes = Hex.decodeHex(cipherText.toCharArray());
            
            // Setup
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            int ctLength = cipherBytes.length;
            byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
            int ptLength = cipher.update(cipherBytes, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
            return new String(plainText);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException 
                | InvalidKeyException | InvalidAlgorithmParameterException 
                | ShortBufferException | IllegalBlockSizeException 
                | BadPaddingException | DecoderException e)
        {
            throw new IllegalStateException("Can't encrypt input!", e);
        }
    }
}
