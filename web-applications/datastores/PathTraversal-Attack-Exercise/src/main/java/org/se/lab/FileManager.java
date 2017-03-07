package org.se.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileManager
{
	private static final String BASE_DIRECTORY = "./doc";
	
	public String readFile(final String name) 
		throws IOException
	{
		if(name == null)
			throw new IllegalArgumentException();
			
		File file = new File(BASE_DIRECTORY, name);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);

		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null)
		{
			buffer.append(line).append("\n");
		}
		in.close();

		return buffer.toString();
	}
}
