package org.se.lab;

import java.security.SecureRandom;
import java.text.ParseException;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JWT_HMACProtectedTest
{
	private static final String hmacKey = "13f266e39f09361ee11dc862ffc2a6571b582a2e97bf62ec2ee8873507f8ecd8";
	
	@Test
	public void testRandomKey()
	{
		byte[] sharedKey = new byte[32];
		new SecureRandom().nextBytes(sharedKey);

		System.out.println(Hex.toHexString(sharedKey));
	}
	
	
	@Test
	public void testProducer() throws KeyLengthException, JOSEException
	{
		// Create Payload
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("joe")
				//.expirationTime(new Date(new Date().getTime() + 60 * 1000))
				.build();
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
		SignedJWT signedJWT = new SignedJWT(header , claimsSet);
		
		// Sign JWT using a HMAC
		byte[] privateKey = Hex.decode(hmacKey);
		JWSSigner signer = new MACSigner(privateKey); 
		signedJWT.sign(signer);
		
		// toString
		String jwt = signedJWT.serialize();

		Assert.assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2UifQ.PQqNnqsJwM1KvTsU3stupd-gshy6brenLuw_iWv6LTI", jwt);
	}
	
	@Test
	public void testConsumer() throws ParseException, JOSEException
	{
		// Parse JWT
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2UifQ.PQqNnqsJwM1KvTsU3stupd-gshy6brenLuw_iWv6LTI";
		SignedJWT signedJWT = SignedJWT.parse(jwt);
		
		// Validate signature
		byte[] privateKey = Hex.decode(hmacKey);	
		JWSVerifier verifier = new MACVerifier(privateKey);
		Assert.assertTrue(signedJWT.verify(verifier));
		
		Assert.assertEquals("joe", signedJWT.getJWTClaimsSet().getSubject());
	}	
}
