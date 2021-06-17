package org.se.lab;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;


public class ZipInspectionTest
{
	private static final String BASE_DIRECTORY = "./src/test/resources";

	@Test
	public void testZipFile() throws IOException
	{
		File file = new File(BASE_DIRECTORY,"demo.zip");
		long compressedSize = file.length();
		System.out.println("Compressed size         = " + compressedSize);

		ZipFile zip = new ZipFile(file);
		long totalUncompressedSize = 0;
		Enumeration<? extends ZipEntry> entry = zip.entries();
		while(entry.hasMoreElements())
		{
			ZipEntry e = entry.nextElement();
			totalUncompressedSize += e.getSize();
		}
		System.out.println("Total Uncompressed Size = " + totalUncompressedSize);
	}
}
