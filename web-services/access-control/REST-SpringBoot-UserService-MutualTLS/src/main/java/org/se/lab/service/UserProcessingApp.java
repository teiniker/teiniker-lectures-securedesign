package org.se.lab.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class UserProcessingApp
{
	static {

		String path = System.getProperty("user.dir");
		String keystorePath = path + File.separator + "keystore.jks";
		File file = new File(keystorePath);

		if (file.exists()) {
			System.setProperty("javax.net.ssl.trustStore", keystorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", "springboot");
		}
	}

	public static void main(String[] args)
	{
		SpringApplication.run(UserProcessingApp.class, args);
	}
}
