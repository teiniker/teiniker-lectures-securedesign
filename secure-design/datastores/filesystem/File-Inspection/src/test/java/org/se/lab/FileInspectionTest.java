package org.se.lab;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FileInspectionTest
{
	private static final String BASE_DIRECTORY = "./src/test/resources";
	private Tika tika = null;

	@Before
	public void setup()
	{
		tika = new Tika();
	}

	@Test
	public void testJPGFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY, "tux");
		Assert.assertEquals("image/jpeg", tika.detect(file));
	}

	@Test
	public void testPNGFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY, "gnu");
		Assert.assertEquals("image/png", tika.detect(file));
	}

	@Test
	public void testPDFFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY, "document1");
		Assert.assertEquals("application/pdf", tika.detect(file));
	}

	@Test
	public void testWordFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY, "document2");	// MS Word
		Assert.assertEquals("application/x-tika-ooxml", tika.detect(file));
	}

	@Test
	public void testExeFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY, "arduino");	// exe
		Assert.assertEquals("application/x-msdownload; format=pe32", tika.detect(file));
	}

}
