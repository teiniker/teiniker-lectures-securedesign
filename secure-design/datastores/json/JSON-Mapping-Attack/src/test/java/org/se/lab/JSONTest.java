package org.se.lab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JSONTest
{
    // JSON injection attack
    @Test(expected= UnrecognizedPropertyException.class)
    public void testJSONInjection2() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        String json =
                """
                    {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "hack":666}
                """;
        Book book = mapper.readValue(json, Book.class);
    }

    // JSON missing attributes
    @Test
    public void testJSONMissingAttributes() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        String json =
                """
                    {"author":"Joshua Bloch","isbn":"978-0134685991"}
                """;

        Book book = mapper.readValue(json, Book.class);

        Assert.assertEquals(0, book.getId());   // No value mapped
        Assert.assertEquals("Joshua Bloch", book.getAuthor());
        Assert.assertNull(book.getTitle());             // No value mapped
        Assert.assertEquals("978-0134685991", book.getIsbn());
    }

    @Test
	public void testJSONInjection() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String json =
		"""
			{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "id":666}
		""";

		Book book = mapper.readValue(json, Book.class);

		Assert.assertEquals(666, book.getId());	// Attack worked !!!!!!
		Assert.assertEquals("Joshua Bloch", book.getAuthor());
		Assert.assertEquals("Effective Java", book.getTitle());
		Assert.assertEquals("978-0134685991", book.getIsbn());
	}

    @Test(expected = JsonProcessingException.class)
    public void testJSONValidation() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        String json =
                """
                    {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "id":666}
                """;

        // Validate
        mapper.readTree(json);
    }

}
