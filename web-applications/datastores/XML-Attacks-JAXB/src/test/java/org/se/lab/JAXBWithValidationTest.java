package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class JAXBWithValidationTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance("org.se.lab");
	}
	
	
	public void testTagInjection() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "session-tag-injection.xml")));

		parseXmlWithSchemaValidation(src);
	}

	@Test
	public void testTagOverriding() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "item-tag-overriding.xml")));

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/main/resources/xsd", "session.xsd"));

		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);  // validate XML against XSD
		JAXBElement<Item> element = unmarshaller.unmarshal(src, Item.class);
		Item item = element.getValue();
		
		Assert.assertEquals("Widget", item.getDescription());
		Assert.assertEquals(BigInteger.valueOf(1000), item.getQuantity());	// should be 1
		Assert.assertEquals(new BigDecimal("1.0"), item.getPrice());		// should be 500.0	
	}

	@Test
	public void testExternalEntityAttack() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "session-entity-file.xml")));

		parseXmlWithSchemaValidation(src);
	}
	
	@Test
	public void testXMLBomb() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "session-entity-expansion.xml")));

		parseXmlWithSchemaValidation(src);
	}
	
	/*
	 * Utility methods
	 */
	
	private void parseXmlWithSchemaValidation(Source src) throws SAXException, JAXBException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/main/resources/xsd", "session.xsd"));		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);  // validate XML against XSD
		JAXBElement<SessionRootType> element = unmarshaller.unmarshal(src, SessionRootType.class);
		SessionRootType root = element.getValue();

		Assert.assertEquals(2, root.getSessions().size());
		
		SessionType s1 = root.getSessions().get(0);
		Assert.assertEquals("one", s1.getId());

		SessionType s2 = root.getSessions().get(1);
		Assert.assertEquals("two", s2.getId());
	}
}
