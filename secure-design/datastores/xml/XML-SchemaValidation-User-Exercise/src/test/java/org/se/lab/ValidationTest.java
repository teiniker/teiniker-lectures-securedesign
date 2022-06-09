package org.se.lab;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

public class ValidationTest
{
	@Test
	public void testValidUser() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "user.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "user.xml")));
		validator.validate(xml);
	}

	@Test
	public void testInvalidUser_Injection() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "user.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "user-injection.xml")));

		try
		{
			validator.validate(xml);
			Assert.fail();
		}
		catch(SAXParseException e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}

	@Test
	public void testInvalidUser_MissingPassword() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "user.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "user-missing-password.xml")));

		try
		{
			validator.validate(xml);
			Assert.fail();
		}
		catch(SAXParseException e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}

	@Test
	public void testInvalidUser_MissingId() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "user.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "user-missing-id.xml")));

		try
		{
			validator.validate(xml);
			Assert.fail();
		}
		catch(SAXParseException e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}

	@Test
	public void testInvalidUser_InvalidMailAddress() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "user.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "user-invalid-mail.xml")));

		try
		{
			validator.validate(xml);
			Assert.fail();
		}
		catch(SAXParseException e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}

}
