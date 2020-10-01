package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class OrderAttacksTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance(Order.class);
	}
		
	@Test
	public void testXmlInjection() throws JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "order-injection.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<Order> element = unmarshaller.unmarshal(src, Order.class);
		Order order = element.getValue();

		Assert.assertEquals("FHJ-20151020-007", order.getName());
		OrderLine line = order.getLines().get(0);
		Assert.assertEquals(999, line.getQuantity());
		Product product = line.getProduct();
		Assert.assertEquals(666, product.getPrice());
	}
}
