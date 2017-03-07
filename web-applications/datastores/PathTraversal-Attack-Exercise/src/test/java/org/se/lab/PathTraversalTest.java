package org.se.lab;

import java.io.IOException;

import org.junit.Test;


public class PathTraversalTest
{
	@Test
	public void testReadFile() throws IOException
	{
		FileManager handler = new FileManager();		
		String txt = handler.readFile("TODO.txt");
		System.out.println(txt);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testPathManipulation() throws IOException
	{
		FileManager handler = new FileManager();		
		String txt = handler.readFile("../src/main/java/org/se/lab/FileManager.java");
		System.out.println(txt);		
	}
}
