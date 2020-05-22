package org.se.lab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLine
	extends Entity
{
	/*
	 * Constructor
	 */
    protected OrderLine() {}
    
	public OrderLine(long id, int quantity, Product product)
	{
		setId(id);
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
	
    public String toString()
	{
		return getId() + "," + getQuantity() + "," + getProduct().getId();
	}
}
