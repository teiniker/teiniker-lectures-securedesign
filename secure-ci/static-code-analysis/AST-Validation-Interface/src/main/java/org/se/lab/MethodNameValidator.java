package org.se.lab;

import org.se.lab.ast.*;

import java.util.ArrayList;
import java.util.List;


public class MethodNameValidator
	extends AbstractValidator
{
	// List of method names with upper case
	private List<String> upperCaseMethods = new ArrayList<String>();
	public List<String> getUpperCaseMethods()
	{
		return upperCaseMethods;
	}
	public void setUpperCaseMethods(List<String> names)
	{
		this.upperCaseMethods = names;
	}
	
	
	public void visit(MOperation op)
	{
		checkUpperCase(op, op.getName());
		super.visit(op);
	}

	
	/*
	 * Helper methods
	 */
	
	private void checkUpperCase(Object o, String name)
	{
		// check if the name is invalid regarding upper/lower case
		if ( Character.isUpperCase( name.charAt(0) ) == true )
		{
			// if so, add it to the list of upper case method names
			upperCaseMethods.add(name);
		}
	}
}
