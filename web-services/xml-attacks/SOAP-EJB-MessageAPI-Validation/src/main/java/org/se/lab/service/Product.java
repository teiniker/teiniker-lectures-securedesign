package org.se.lab.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Product
{
	/*
	 * Constructor
	 */
    protected Product() {}
    
	public Product(String description, long price)
	{
		setDescription(description);
		setPrice(price);
	}
		
	
	/*
	 * Property: description:String
	 */
	@XmlElement
	private String description;
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		if(description == null)
			throw new IllegalArgumentException();
		this.description = description;
	}
	
	
	/*
	 * Property: price:long
	 */
	@XmlElement
	private long price;
	public long getPrice()
	{
		return price;
	}
	public void setPrice(long price)
	{
		if(price < 0)
			throw new IllegalArgumentException();
		this.price = price;
	}
	
	
	/*
	 * Object methods
	 */
	@Override
	public String toString()
	{
		return getDescription() + "," + getPrice();
	}	
}
