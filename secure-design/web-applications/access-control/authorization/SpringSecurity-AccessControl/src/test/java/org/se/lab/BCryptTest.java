package org.se.lab;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptTest 
{
	@Test
	public void testEncoder()
	{
		String password = "bart";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
	
		System.out.println(hashedPassword);
	}
}
