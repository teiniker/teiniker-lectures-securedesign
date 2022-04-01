package org.se.lab;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


public class CanonicalPathTest
{
	private static final String BASE_DIRECTORY = "./doc";

	/*
	 *  Canonicalize path names before validating them
	 *  
	 *  The getCanonicalPath() method, introduced in Java 2, because it 
	 *  resolves all aliases, shortcuts, and symbolic links consistently 
	 *  across all platforms. 
	 *  Special file names such as dot dot (..) are also removed so that 
	 *  the input is reduced to a canonicalized form before validation is 
	 *  carried out. 
	 */
	@Test
	public void testCanonicalName() throws IOException
	{
		File f = new File(BASE_DIRECTORY, "../../se/lab/FileManager.java");
		System.out.println("Relative Path : " + f.getPath());
		System.out.println("Absolute Path : " + f.getAbsolutePath());
		System.out.println("CanonicalPath : " + f.getCanonicalPath());

		File baseDir = new File(BASE_DIRECTORY);
		String basePath = baseDir.getCanonicalPath();
		System.out.println("Base Directory: " + basePath);
		Assert.assertFalse(f.getCanonicalPath().startsWith(basePath));
	}
}
