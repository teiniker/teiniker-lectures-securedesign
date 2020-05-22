package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class OrderValidationTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance(Order.class);
	}
		
	@Test
	public void testOrder() throws SAXException, JAXBException, FileNotFoundException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "order.xsd"));

		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "order.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);

		JAXBElement<Order> element = unmarshaller.unmarshal(src, Order.class);
		Order order = element.getValue();

		Assert.assertEquals("FHJ-20151020-007", order.getName());
	}

	@Test
	public void testInvalidOrder() throws SAXException, JAXBException, FileNotFoundException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/test/resources/xsd", "order.xsd"));

		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "order-injection.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);

		try
		{
			JAXBElement<Order> element = unmarshaller.unmarshal(src, Order.class);
			Order order = element.getValue();
			Assert.fail();
		}
		catch(UnmarshalException e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}
}
