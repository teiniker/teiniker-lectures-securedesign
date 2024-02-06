package org.se.lab.ast;

import java.util.ArrayList;
import java.util.List;

public class MPackage
	extends MNamedElement
{
	/*
	 * Constructor
	 */
	public MPackage(String name)
	{
		super(name);
	}
	
	
	/*
	 * Reference: ---[*]-> MInterface
	 */
	List<MInterface> interfaces = new ArrayList<MInterface>();
	public List<MInterface> getInterfaces()
	{
		return interfaces;
	}
	public void setInterfaces(List<MInterface> interfaces)
	{
		this.interfaces = interfaces;
	}
}
