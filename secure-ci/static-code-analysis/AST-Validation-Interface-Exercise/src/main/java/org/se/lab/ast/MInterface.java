package org.se.lab.ast;

import java.util.ArrayList;
import java.util.List;

public class MInterface
	extends MType
{
	/*
	 * Constructor
	 */
	public MInterface(String name)
	{
		super(name);
	}
	
	
	/*
	 * Property: pub:boolean
	 */
	private boolean pub;
	public boolean isPublic()
	{
		return pub;
	}
	public void setPublic(boolean pub)
	{
		this.pub = pub;
	}
	
	
	/*
	 * Reference: ---[*]-> MOperation
	 */
	List<MOperation> operations = new ArrayList<MOperation>();
	public List<MOperation> getOperations()
	{
		return operations;
	}
	public void setOperations(List<MOperation> operations)
	{
		this.operations = operations;
	}
}
