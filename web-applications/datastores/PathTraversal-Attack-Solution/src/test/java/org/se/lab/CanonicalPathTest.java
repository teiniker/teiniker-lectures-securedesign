package org.se.lab;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class CanonicalPathTest
{
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
		
		File f = new File("../src/org/se/lab/../../se/lab/FileManager.java");
		System.out.println(f.getPath());
		System.out.println(f.getAbsolutePath());
		
		System.out.println(f.getCanonicalPath());
	}
}
