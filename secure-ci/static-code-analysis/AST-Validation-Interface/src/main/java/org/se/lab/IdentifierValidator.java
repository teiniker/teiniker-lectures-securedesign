package org.se.lab;

import org.se.lab.ast.*;

public class IdentifierValidator
	extends AbstractValidator
{
	public void visit(MPackage pkg)
	{
		checkName(pkg, pkg.getName());
		super.visit(pkg);
	}
	
	public void visit(MInterface iface)
	{
		checkName(iface, iface.getName());
		super.visit(iface);
	}
	
	public void visit(MOperation op)
	{
		checkName(op, op.getName());
		super.visit(op);
	}
		
	public void visit(MParameter par)
	{
		checkName(par, par.getName());
		super.visit(par);
	}
	
	public void visit(MType type)
	{
		checkName(type, type.getName());
	}
	
	
	/*
	 * Helper methods
	 */
	
	private void checkName(Object o, String name)
	{
		if(name== null || name.trim().length() == 0)
			throw new IllegalStateException("Invalid identifier " + name + " at " + o.getClass().getTypeName());
	}
}
