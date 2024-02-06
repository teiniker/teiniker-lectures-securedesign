package org.se.lab.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLine
{
	/*
	 * Constructor
	 */
    protected OrderLine() {}
    
	public OrderLine(int quantity, Product product)
	{
		setQuantity(quantity);
		setProduct(product);
	}
	

	/*
	 * Property: quantity:int
	 */
	@XmlElement
	private int quantity;
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		if(quantity < 0)
			throw new IllegalArgumentException();
		this.quantity = quantity;
	}
	
	
	/*
	 * Association: ---[1]-> Product
	 */
	@XmlElement
	private Product product;
	public Product getProduct()
	{
	    return product;
	}
	public void setProduct(Product product)
	{
	    if(product == null)
	        throw new IllegalArgumentException();
	    this.product = product;
	}
	
			
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
	    return "OrderLine [quantity=" + quantity + ", product=" + product + "]";
	}
	
}
