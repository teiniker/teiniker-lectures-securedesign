package org.se.lab.ast;

import java.util.ArrayList;
import java.util.List;

public class MOperation
	extends MTypedElement
{
	/*
	 * Constructor
	 */
	public MOperation(String name, MType type)
	{
		super(name,type);
	}
	
	
	/*
	 * Reference: ---[*]-> parameters:MParameter[*]
	 */
	private List<MParameter> parameters = new ArrayList<MParameter>();
	public List<MParameter> getParameters()
	{
		return parameters;
	}
	public void setParameters(List<MParameter> parameters)
	{
		if(parameters == null)
			throw new IllegalArgumentException();
		this.parameters = parameters;
	}
}
