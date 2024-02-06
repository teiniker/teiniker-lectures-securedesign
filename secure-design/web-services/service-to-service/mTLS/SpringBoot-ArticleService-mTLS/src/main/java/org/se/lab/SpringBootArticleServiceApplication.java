package org.se.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SpringBootArticleServiceApplication
{
	static
	{
		String path = System.getProperty("user.dir");
		String keystorePath = path + File.separator + "src/main/resources/server.jks";
		File file = new File(keystorePath);

		if (file.exists())
		{
			System.out.println("Path (trustStore): " + file.getAbsolutePath());
			System.setProperty("javax.net.ssl.trustStore", keystorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", "student");
		}
	}

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootArticleServiceApplication.class, args);
	}
}
