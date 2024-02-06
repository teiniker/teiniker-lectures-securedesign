package org.se.lab;

import org.junit.Assert;
import org.junit.Test;

public class AddressBookTest
{
	@Test
	public void testAddressBookBuilder()
	{
		Person homer = Person.newBuilder()
				.setName("homer")
				.setPassword("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S")
				.setRole(Role.USER)
				.build();
		Person marge = Person.newBuilder()
				.setName("marge")
				.setPassword("$2y$12$GxlzYasVmEnH7G0OuRXWx..G.NqjA7mf20ZWqruD8endmfdmHajMW ")
				.setRole(Role.ADMIN)
				.build();

		AddressBook book = AddressBook.newBuilder()
				.addPeople(marge)
				.addPeople(homer)
				.build();

		Assert.assertEquals(2, book.getPeopleCount());
		Assert.assertEquals("marge", book.getPeople(0).getName());
		Assert.assertEquals("homer", book.getPeople(1).getName());
	}

	@Test
	public void testAddressBookToString()
	{
		Person homer = Person.newBuilder()
				.setName("homer")
				.setPassword("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S")
				.setRole(Role.USER)
				.build();
		Person marge = Person.newBuilder()
				.setName("marge")
				.setPassword("$2y$12$GxlzYasVmEnH7G0OuRXWx..G.NqjA7mf20ZWqruD8endmfdmHajMW ")
				.setRole(Role.ADMIN)
				.build();

		AddressBook book = AddressBook.newBuilder().addPeople(marge).addPeople(homer).build();
		System.out.println(book.toString());
	}
}
