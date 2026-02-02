package org.se.lab;

import org.springframework.boot.SpringApplication;

import java.io.File;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication
{
    static
    {
        String path = System.getProperty("user.dir");
        String keystorePath = path + File.separator + "src/main/resources/server.jks";
        File file = new File(keystorePath);
        if (file.exists())
        {
            System.setProperty("javax.net.ssl.trustStore", keystorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", "student");
        }
    }

    public static void main(String[] args)
	{
		SpringApplication.run(SpringBootApplication.class, args);
	}
}
