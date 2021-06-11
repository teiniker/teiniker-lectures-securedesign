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
	public void testOrderValidation() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "order.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "order.xml")));
		validator.validate(xml);
	}

	@Test
	public void testInvalidOrder() throws IOException, SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "order.xsd"));
		Validator validator = xsd.newValidator();

		Source xml = new StreamSource(new FileReader(new File("src/test/resources/xml", "order-injection.xml")));

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
