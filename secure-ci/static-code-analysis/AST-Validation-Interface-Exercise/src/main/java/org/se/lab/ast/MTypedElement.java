package org.se.lab.ast;

public abstract class MTypedElement
	extends MNamedElement
{
	/*
	 * Constructor
	 */
	public MTypedElement(String name, MType type)
	{
		super(name);
		setType(type);
	}
	
	
	/*
	 * Reference: ---[1]-> type:MType
	 */
	private MType type;
	public MType getType()
	{
		return type;
	}
	public void setType(MType type)
	{
		this.type = type;
	}	
}
