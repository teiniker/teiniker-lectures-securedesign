package org.se.lab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JSONTest
{
	@Test
	public void testJSON2Java() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String json =
		"""
			{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
		""";
		Book book = mapper.readValue(json, Book.class);

		Assert.assertEquals(1, book.getId());
		Assert.assertEquals("Joshua Bloch", book.getAuthor());
		Assert.assertEquals("Effective Java", book.getTitle());
		Assert.assertEquals("978-0134685991", book.getIsbn());
	}

	@Test
	public void testJava2JSON() throws JsonProcessingException
	{
		Book book = new Book(1, "Joshua Bloch", "Effective Java", "978-0134685991");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(book);

		String expected =
		"""
			{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
		""";

		Assert.assertEquals(expected.trim(), json);
	}

	@Test
	public void JSONArray2JavaArray() throws JsonProcessingException
	{
		String jsonArray =
		"""
			[
				{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
				{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
				{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}
			]
		""";

		ObjectMapper mapper = new ObjectMapper();
		Book[] books = mapper.readValue(jsonArray, Book[].class);

		System.out.println(Arrays.toString(books));
		Assert.assertEquals("978-0134685991", books[0].getIsbn());
		Assert.assertEquals("978-0132350884", books[1].getIsbn());
		Assert.assertEquals("978-0134757599", books[2].getIsbn());
	}

	@Test
	public void JavaArray2JSONArray() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		Book[] books =
		{
			new Book(1, "Joshua Bloch", "Effective Java", "978-0134685991"),
			new Book(2, "Robert C. Martin", "Clean Code", "978-0132350884"),
			new Book(3, "Martin Fowler", "Refactoring", "978-0134757599")
		};

		String jsonArray = mapper.writeValueAsString(books);
		System.out.println(jsonArray);

		String expected =
		"""
			[{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}]
		""";
		Assert.assertEquals(expected.trim(), jsonArray);
	}

	@Test
	public void JSONArray2JavaList() throws JsonProcessingException
	{
		String jsonArray =
		"""
		[
			{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
			{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
			{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}
		]
		""";

		ObjectMapper mapper = new ObjectMapper();
		List<Book> books = mapper.readValue(jsonArray, new TypeReference<List<Book>>() {
		});

		System.out.println(books);
		Assert.assertEquals("978-0134685991", books.get(0).getIsbn());
		Assert.assertEquals("978-0132350884", books.get(1).getIsbn());
		Assert.assertEquals("978-0134757599", books.get(2).getIsbn());
	}

	@Test
	public void JavaList2JSONArray() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		List<Book> books = Arrays.asList(
				new Book(1, "Joshua Bloch", "Effective Java", "978-0134685991"),
				new Book(2, "Robert C. Martin", "Clean Code", "978-0132350884"),
				new Book(3, "Martin Fowler", "Refactoring", "978-0134757599")
		);

		String jsonArray = mapper.writeValueAsString(books);
		System.out.println(jsonArray);

		String expected =
		"""
		  [{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}]
		""";
		Assert.assertEquals(expected.trim(), jsonArray);
	}
}
