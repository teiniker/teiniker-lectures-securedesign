package org.se.lab;

import java.io.IOException;

public class Main
{
	/* Run the application as root:
	 * 
	 * $ su
	 * root66
	 * 
	 * # java -cp ./target/classes/ org.se.lab.Main
	 */	
	public static void main(String[] args) throws IOException
	{
		FileManager handler = new FileManager();		
		String txt = handler.readFile("../../../../../../../../etc/shadow");
		System.out.println(txt);		
	}
}
