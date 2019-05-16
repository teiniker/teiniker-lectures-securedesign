package org.se.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order
{
	/*
	 * Constructor
	 */
    protected Order()
    {      
    }
    
	public Order(String name)
	{
		setName(name);
	}

	
	/*
     * Property: id
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
	 * Property: name:String
	 */
	@XmlElement
	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		if(name == null)
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/*
	 * Association: ---[*]-> OrderLines
	 */
	private List<OrderLine> lines = new ArrayList<>();
    public List<OrderLine> getLines()
    {
        return lines;
    }
    public void setLines(List<OrderLine> lines)
    {
        if(lines == null)
            throw new IllegalArgumentException();
        this.lines = lines;
    }

	    
    /*
     * Object methods
     */
    
    @Override
    public String toString()
    {
        return "Order [id=" + id + ", name=" + name + ", lines=" + lines + "]";
    }
    
//    @Override
//    public String toString()
//    {
//        return "Order [name=" + name + ", lines=" + lines + "]";
//    }
   
}
