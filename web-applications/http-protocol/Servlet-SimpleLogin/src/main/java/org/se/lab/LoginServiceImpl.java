package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.codec.binary.Hex;

public class LoginServiceImpl implements LoginService
{
	/*
	 * Simulate a database table
	 */
	private ConcurrentMap<String,String> table = new ConcurrentHashMap<String,String>();
	
	public LoginServiceImpl()
	{
	    // Simulate a database
		table.put("student", "264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb");
		table.put("homer",   "e22115b5d76640e2389bcac25c46a2df03b2df657c5b3ff32cbaed141f8e8ef0");
		table.put("marge",   "b4b811fa40505329ae871e52f03527c3720c9af7fb8607819658535c5484c41e");
		table.put("bart",    "9551dadbf76a27457946e70d1aebebe2132f8d3bce6378d216c11853524dd3a6");
		table.put("lisa",    "d84fe7e07bedb227cffff10009151d96fc944f6a1bd37cff60e8e4626a1eb1c3");
		table.put("maggie",  "aae5be5f6474904b686f639e0fcfd2be440121cd889fa381a94b71750758345e");
	}
	
	
	@Override
	public boolean login(String username, String password, String usergroup)
	{
		if(table.containsKey(username))
		{
            if(table.get(username).equals(calculateHashString(password)))
            {
                return true;
            }
            else
            {
                return false;
            }
		}
		else
		{
			return false;
		}
	}

	
	
	private String calculateHashString(final String message)
	{
		try
        {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            algorithm.update(message.getBytes("UTF-8"));
            byte[] bytes = algorithm.digest();
            return Hex.encodeHexString(bytes);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new IllegalStateException("Can't calculate hash value!", e);
        }
	}
}
