package org.se.lab.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDTO
{
	/*
	 * Constructor
	 */
    protected ProductDTO() 
    {
        this(0, "", 0);
    }
    
	public ProductDTO(int id, String description, long price)
	{
		setId(id);
		setDescription(description);
		setPrice(price);
	}
		
	
    /*
     * Property: id:int
     */
    @XmlAttribute
    private int id;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
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
	    return "ProductDTO [id=" + id + ", description=" + description + ", price=" + price + "]";
	}

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductDTO other = (ProductDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
