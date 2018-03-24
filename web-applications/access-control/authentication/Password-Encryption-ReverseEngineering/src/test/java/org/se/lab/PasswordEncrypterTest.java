package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

public class PasswordEncrypterTest
{
    @Test
    public void testCheckPassword() throws UnsupportedEncodingException, DecoderException
    {
        String saltString = "c54fcad9814b6e225214780fe09a8ea5";
        byte[] password = "Trink4Bier".getBytes("UTF-8");
        String hashValue = "c54fcad9814b6e225214780fe09a8ea5bca753e996e11e1147cb59b74faed888ac6d4ca017b605b9a598d4996ea1d43f";

        byte[] result = encryptPassword(Hex.decodeHex(saltString.toCharArray()), password);
        Assert.assertEquals(hashValue, Hex.encodeHexString(result));
    }

    @Test
    public void testNumberOfIterations() throws UnsupportedEncodingException, DecoderException
    {
        String saltString = "c54fcad9814b6e225214780fe09a8ea5";
        byte[] password = "Trink4Bier".getBytes("UTF-8");
        String hashValue = "c54fcad9814b6e225214780fe09a8ea5bca753e996e11e1147cb59b74faed888ac6d4ca017b605b9a598d4996ea1d43f";

        for (int i = 0; i < 100; i++)
        {
            byte[] result = encryptPasswordWithIterations(Hex.decodeHex(saltString.toCharArray()), password, i);
            if (hashValue.equals(Hex.encodeHexString(result)))
            {
                System.out.println("# iterations = " + i);
                break;
            }
        }
    }
    
    @Test
    public void testOneIteration() throws UnsupportedEncodingException, DecoderException
    {
        String saltString = "c54fcad9814b6e225214780fe09a8ea5";
        byte[] password = "Trink4Bier".getBytes("UTF-8");

        byte[] result = encryptPasswordWithIterations(Hex.decodeHex(saltString.toCharArray()), password, 1);
        System.out.println("1 iteration => " + Hex.encodeHexString(result));
    }
    

    protected byte[] encryptPassword(byte[] salt, byte[] password)
    {
        return encryptPasswordWithIterations(salt, password, 66);
    }
    
    protected byte[] encryptPasswordWithIterations(byte[] salt, byte[] password, int iterations)
    {
        try
        {
            // value = [salt]+[password]
            byte[] hash = new byte[salt.length + password.length];
            System.arraycopy(salt, 0, hash, 0, salt.length);
            System.arraycopy(password, 0, hash, salt.length, password.length);

            // hash (salt+password)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (int i = 0; i < iterations; i++)
            {
                md.update(hash);
                hash = md.digest();
                System.out.println(i + " : hash  : " + Hex.encodeHexString(hash) + " " + hash.length + " bytes ");
            }

            // result = [salt]+[hash]
            byte[] result = new byte[hash.length + salt.length];
            System.arraycopy(salt, 0, result, 0, salt.length);
            System.arraycopy(hash, 0, result, salt.length, hash.length);

            System.out.println("hash  : " + Hex.encodeHexString(hash) + " " + hash.length + " bytes ");
            System.out.println("salt  : " + Hex.encodeHexString(salt) + " " + salt.length + " bytes ");
            System.out.println("result: " + Hex.encodeHexString(result) + " " + result.length + " bytes ");

            return result;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
