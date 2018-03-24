package org.se.lab;

import java.io.UnsupportedEncodingException;

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
        // TODO
    }
    
    protected byte[] encryptPassword(byte[] salt, byte[] password)
    {
        // TODO
        return null;
    }
    
    protected byte[] encryptPasswordWithIterations(byte[] salt, byte[] password, int n)
    {
        // TODO
        return null;
    }
}
