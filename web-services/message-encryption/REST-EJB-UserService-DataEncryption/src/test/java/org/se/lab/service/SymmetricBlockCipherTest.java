package org.se.lab.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class SymmetricBlockCipherTest
{
    @Test
    public void testAES_CTR_Random()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException
    {
        byte[] input = new byte[]
        { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x00, 0x01,
                0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };

        byte[] ivBytes = new byte[16]; // Cipher block size
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);

        System.out.println("input : " + Hex.encodeHexString(input) + " bytes: " + input.length);

        // Setup
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        System.out.println("key   : " + Hex.encodeHexString(key.getEncoded()) + " bytes: " + key.getEncoded().length);

        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        System.out.println("iv    : " + Hex.encodeHexString(ivSpec.getIV()) + " bytes: " + ivSpec.getIV().length);

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        System.out.println("block size: " + cipher.getBlockSize() + " bytes");

        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        System.out.println("cipher: " + Hex.encodeHexString(cipherText) + " bytes: " + ctLength);

        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] plainText = new byte[cipher.getOutputSize(ctLength)];

        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        ptLength += cipher.doFinal(plainText, ptLength);
        System.out.println("plain : " + Hex.encodeHexString(plainText) + " bytes: " + ptLength);
    }

    @Test
    public void testAES_CTR() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException,
            InvalidAlgorithmParameterException, DecoderException
    {
        byte[] input = Hex.decodeHex("000102030405060708090a0b0c0d0e0f0001020304050607".toCharArray());
        byte[] ivBytes = Hex.decodeHex("cbfd3ac00c3019a0ae62658dc1dcaabf".toCharArray());
        byte[] keyBytes = Hex.decodeHex("78dac5d9619a3a10bcab780ecc3f00a6".toCharArray());

        System.out.println("input : " + Hex.encodeHexString(input) + " bytes: " + input.length);

        // Setup
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        System.out.println("key   : " + Hex.encodeHexString(key.getEncoded()) + " bytes: " + key.getEncoded().length);

        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        System.out.println("iv    : " + Hex.encodeHexString(ivSpec.getIV()) + " bytes: " + ivSpec.getIV().length);

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        System.out.println("block size: " + cipher.getBlockSize() + " bytes");

        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        System.out.println("cipher: " + Hex.encodeHexString(cipherText) + " bytes: " + ctLength);

        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] plainText = new byte[cipher.getOutputSize(ctLength)];

        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        ptLength += cipher.doFinal(plainText, ptLength);
        System.out.println("plain : " + Hex.encodeHexString(plainText) + " bytes: " + ptLength);
    }

    @Test
    public void testAES_CTRRefactored() throws DecoderException
    {
        String input = "Hallo KBerg!";
        byte[] ivBytes = Hex.decodeHex("cbfd3ac00c3019a0ae62658dc1dcaabf".toCharArray());
        byte[] keyBytes = Hex.decodeHex("78dac5d9619a3a10bcab780ecc3f00a6".toCharArray());
        
        System.out.println("input       : " + input);
        
        String cipherString = encryptToString(keyBytes, ivBytes, input);
        System.out.println("cipherString: " + cipherString);
                
        String plainString = decryptToString(keyBytes, ivBytes, cipherString);
        System.out.println("plainString : " + plainString);
        
    }
    
    
    public String encryptToString(byte[] keyBytes, byte[] ivBytes, String inputString)
    {
        try
        {
            byte[] input = inputString.getBytes();
            
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
                | ShortBufferException | IllegalBlockSizeException | BadPaddingException e)
        {
            throw new IllegalStateException("Can't encrypt input!", e);
        }
    }
    
    
    public String decryptToString(byte[] keyBytes, byte[] ivBytes, String cipherText)
    {
        try
        {
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
