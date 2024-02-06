package org.se.lab;

import org.se.lab.ast.*;

public abstract class AbstractValidator
	implements InterfaceVisitor
{
	@Override
	public void visit(MPackage pkg)
	{
		// Validation
		
		// Naviagtion
		for(MInterface iface : pkg.getInterfaces())
		{
			visit(iface);
		}
	}

	@Override
	public void visit(MInterface iface)
	{
		// Validation
		
		// Naviagtion
		for(MOperation op : iface.getOperations())
		{
			visit(op);
		}
	}

	@Override
	public void visit(MOperation op)
	{
		// Validation
		
		// Naviagtion
		visit(op.getType());
		for(MParameter par : op.getParameters())
		{
			visit(par);
		}
	}

	@Override
	public void visit(MParameter par)
	{
		// Validation
		
		// Naviagtion
		visit(par.getType());
	}

	@Override
	public void visit(MType type)
	{
		// Validation
	}
}
