package org.se.lab;

import java.io.*;

public class FileManager
{
	private static final String BASE_DIRECTORY = "./doc";
	
	public String readFile(final String name) 
	{
		if(name == null)
			throw new IllegalArgumentException();

		File file = new File(BASE_DIRECTORY, name);

		try(BufferedReader in = new BufferedReader(new FileReader(file)))
		{
			StringBuilder buffer = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null)
			{
				buffer.append(line).append("\n");
			}
			return buffer.toString();
		}
		catch (IOException e)
		{
			throw new IllegalStateException("Can't access file " + name, e);
		}
	}
}
