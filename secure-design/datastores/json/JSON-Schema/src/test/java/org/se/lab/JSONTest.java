package org.se.lab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JSONTest
{
	@Test
	public void testJsonSchema() throws IOException
	{
		String jsonData =
			"""
				{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
			""";

		Set<ValidationMessage> errors = validateSchema("./src/test/resources/book-schema.json", jsonData);

		Assert.assertEquals(0, errors.size());
	}

	@Test
	public void testJsonSchema_InjectionAttack() throws IOException
	{
		String jsonData =
				"""
                    {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "id":666}
                """;

		Set<ValidationMessage> errors = validateSchema("./src/test/resources/book-schema.json", jsonData);

		System.out.println(errors);
		// JSON Schema itself cannot be modified to detect duplicate keys. The JSON Schema specification
		// (including Draft-07) does not offer a mechanism for checking for duplicate keys within an object.
		// The recommended solution is to configure your JSON parser to reject duplicate keys.
		Assert.assertEquals(0, errors.size());
	}

	@Test
	public void testJsonSchemaInvalidType() throws IOException
	{
		String jsonData =
				"""
                    {"id":"one","author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
                """;

		Set<ValidationMessage> errors = validateSchema("./src/test/resources/book-schema.json", jsonData);
		System.out.println(errors);
		Assert.assertEquals(1, errors.size());
	}

	@Test
	public void testJsonSchemaToManyProperties() throws IOException
	{
		String jsonData =
				"""
                    {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "price":1099}
                """;

		Set<ValidationMessage> errors = validateSchema("./src/test/resources/book-schema.json", jsonData);
		System.out.println(errors);
		Assert.assertEquals(1, errors.size());
	}

	/*
	 * Helper method
	 */

	Set<ValidationMessage> validateSchema(String schemaFileName, String jsonData) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(jsonData);

		File schemaFile = new File(schemaFileName);
		Assert.assertTrue(schemaFile.exists());

		JsonNode schemaNode = mapper.readTree(schemaFile);
		// Create a JsonSchemaFactory for Draft-07
		JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
		// Build the JsonSchema object from the parsed schema
		JsonSchema schema = schemaFactory.getSchema(schemaNode);
		// Validate the JSON document against the schema
		Set<ValidationMessage> errors = schema.validate(jsonNode);
		return errors;
	}
}
