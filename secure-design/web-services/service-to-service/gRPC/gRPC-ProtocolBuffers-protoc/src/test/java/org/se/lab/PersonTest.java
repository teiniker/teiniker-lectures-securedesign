package org.se.lab;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class PersonTest
{
	private Person person;

	@Before
	public void setup()
	{
		person = Person.newBuilder()
				.setId(7)
				.setName("homer")
				.setPassword("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S")
				.build();
	}

	@Test
	public void testPerson()
	{
		Assert.assertEquals(7, person.getId());
		Assert.assertEquals("homer", person.getName());
		Assert.assertEquals("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S", person.getPassword());
	}

	@Test
	public void testPersonToString()
	{
		System.out.println(person.toString());
	}

	@Test
	public void testPersonSerialization() throws InvalidProtocolBufferException
	{
		byte[] bytes = person.toByteArray();

		String hexString = Hex.encodeHexString(bytes);
		System.out.println(hexString);

		Person clone = Person.parseFrom(bytes);
		System.out.println(person.toString());
	}

	@Test
	public void testPersonFile() throws IOException
	{
		File file = new File("homer.data");
		FileOutputStream fos = new FileOutputStream(file);
		person.writeTo(fos);
		fos.close();

		FileInputStream fis = new FileInputStream(file);
		Person clone = Person.parseFrom(fis);
		Assert.assertEquals(7, clone.getId());
		Assert.assertEquals("homer", clone.getName());
		Assert.assertEquals("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S", clone.getPassword());
	}
}
