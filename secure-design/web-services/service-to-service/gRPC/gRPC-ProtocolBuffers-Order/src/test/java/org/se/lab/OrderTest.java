package org.se.lab;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderTest
{
	private Order order;

	@Before
	public void setUp()
	{
		Product book = Product.newBuilder()
				.setId(3)
				.setDescription("Effective Java")
				.setPrice(3900)
				.build();

		OrderLine line = OrderLine.newBuilder()
				.setId(2)
				.setQuantity(5)
				.setProduct(book)
				.build();

		order = Order.newBuilder()
				.setId(1)
				.setName("ORDER-20221208")
				.addLines(line)
				.build();
	}

	@Test
	public void testToString()
	{
		System.out.println(order.toString());
	}


	@Test
	public void testOrderToHex()
	{
		byte[] bytes = order.toByteArray();
		Assert.assertEquals("0801120e4f524445522d32303232313230381a1b080210051a150803120e456666656374697665204a61766118bc1e",
				Hex.encodeHexString(bytes));
	}

		@Test
	public void testOrderDeepCopy() throws InvalidProtocolBufferException
	{
		byte[] bytes = order.toByteArray();
		System.out.println(Hex.encodeHexString(bytes));
		Order copy = Order.parseFrom(bytes);

		assertEquals(order, copy);
	}

	// Custom assert methods
	public void assertEquals(Order expected, Order actual)
	{
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getLinesCount(), actual.getLinesCount());

		for(int i=0; i < expected.getLinesCount(); i++)
		{
			Assert.assertEquals(expected.getLines(i), actual.getLines(i));
		}
	}
	public void assertEquals(OrderLine expected, OrderLine actual)
	{
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
		assertEquals(expected.getProduct(), actual.getProduct());
	}
	public void assertEquals(Product expected, Product actual)
	{
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
		Assert.assertEquals(expected.getPrice(), actual.getPrice());
	}
}
