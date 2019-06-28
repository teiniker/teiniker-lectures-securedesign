package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationVisitorTest
{
	private MOperation op;
	
	@Before
	public void setup()
	{
		op = new MOperation("int", new MType("double"));
		
		MParameter p1 = new MParameter("base", new MType("double"));
		MParameter p2 = new MParameter("exp", new MType("double"));
	
		op.getParameters().add(p1);
		op.getParameters().add(p2);
	}

	
	@Test
	public void testVisitor()
	{
		TreeVisitor visitor = new TreeVisitor();
		visitor.visit(op);
	}
}
