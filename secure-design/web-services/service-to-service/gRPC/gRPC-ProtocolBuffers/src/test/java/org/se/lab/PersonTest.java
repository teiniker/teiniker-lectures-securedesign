package org.se.lab;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
				.setRole(Role.ADMIN)
				.build();
	}

	@Test
	public void testPerson()
	{
		Assert.assertEquals(7, person.getId());
		Assert.assertEquals("homer", person.getName());
		Assert.assertEquals("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S", person.getPassword());
		Assert.assertEquals(Role.ADMIN, person.getRole());
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

		System.out.println(Hex.encodeHexString(bytes));

		Person clone = Person.parseFrom(bytes);
		System.out.println(clone.toString());
	}
}
