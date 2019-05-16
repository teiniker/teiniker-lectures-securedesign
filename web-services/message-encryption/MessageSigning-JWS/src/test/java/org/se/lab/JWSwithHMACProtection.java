package org.se.lab;

import java.security.SecureRandom;
import java.text.ParseException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

public class JWSwithHMACProtection
{
    private byte[] key;
    
    @Before
    public void setup() throws DecoderException
    {
        key = Hex.decodeHex("2235a2c49443387aaf08cbe5b59c4a07c1e11d24cad8abe244973a1c1dba4e1d".toCharArray()); 
        
    }
    
    @Test
    public void testRandomSecret()
    {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        System.out.println("sharedSecret   : " + Hex.encodeHexString(sharedSecret));        
    }

    
    @Test
    public void testSinging() throws JOSEException
    {
        // Create HMAC signer
        JWSSigner signer = new MACSigner(key);

        // Prepare JWS object with "Hello, world!" payload
        JWSObject jwsObject = new JWSObject(
                                new JWSHeader(JWSAlgorithm.HS256), 
                                new Payload("Hello, world!"));

        // Apply the HMAC
        jwsObject.sign(signer);

        // To serialize to compact form
        String s = jwsObject.serialize();
        
        System.out.println(s);
    }
    
    @Test
    public void testVerifying() throws ParseException, JOSEException
    {
        String s = "eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.hjtd4y-gFAgObI-ZXB_ZKu3Hi8tzkVBWnWUG_rOANrE";
    
        // To parse the JWS and verify it, e.g. on client-side
        JWSObject jwsObject = JWSObject.parse(s);

        JWSVerifier verifier = new MACVerifier(key);

        Assert.assertTrue(jwsObject.verify(verifier));
        Assert.assertEquals("Hello, world!", jwsObject.getPayload().toString());
    }
}
