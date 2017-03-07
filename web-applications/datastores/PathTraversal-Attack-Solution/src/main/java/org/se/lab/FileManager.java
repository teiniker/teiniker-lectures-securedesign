package org.se.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

/*
 * A Path Traversal attack aims to access files and directories that are 
 * stored outside the web root folder. By browsing the application, the 
 * attacker looks for absolute links to files stored on the web server. 
 * 
 * By manipulating variables that reference files with “dot-dot-slash (../)” 
 * sequences and its variations, it may be possible to access arbitrary 
 * files and directories stored on file system, including application 
 * source code, configuration and critical system files, limited by system 
 * operational access control. The attacker uses “../” sequences to move up 
 * to root directory, thus permitting navigation through the file system.
 */

public class FileManager
{
	private static final String BASE_DIRECTORY = "./doc";
	private static final String FILENAME_REGEX = "^[a-zA-Z0-9 ]{1,25}\\.?[a-zA-Z0-9]{0,3}$"; 
	private final Pattern FILENAME_PATTERN = Pattern.compile(FILENAME_REGEX);
		
	public String readFile(final String name) 
		throws IOException
	{
		if(name == null)
			throw new IllegalArgumentException();
		if(!FILENAME_PATTERN.matcher(Normalizer.normalize(name, Form.NFKC)).matches())
			throw new IllegalArgumentException("Invalid file name: " + name);
			
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
