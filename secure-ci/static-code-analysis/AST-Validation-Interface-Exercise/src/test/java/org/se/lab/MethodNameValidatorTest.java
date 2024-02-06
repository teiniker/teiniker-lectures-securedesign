package org.se.lab;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.ast.*;


public class MethodNameValidatorTest
{
	private MPackage pkg;
	private MethodNameValidator validator;
	
	
	@Before
	public void setup()
	{
		pkg = new MPackage("org.se.lab");
		
		MInterface iface = new MInterface("Stack");
		iface.setPublic(true);		
		pkg.getInterfaces().add(iface);
				
		MOperation push = new MOperation("Push", new MType("void"));
		push.getParameters().add(new MParameter("value", new MType("int")));
		iface.getOperations().add(push);
		iface.getOperations().add(new MOperation("Pull", new MType("void")));
		
		iface.getOperations().add(new MOperation("pop", new MType("int")));
		iface.getOperations().add(new MOperation("top", new MType("int")));
		iface.getOperations().add(new MOperation("isEmpty", new MType("boolean")));
		iface.getOperations().add(new MOperation("isFull", new MType("boolean")));
		
		validator = new MethodNameValidator();
	}
	
	@Test
	public void testInvalidMethodNames()
	{
		validator.visit(pkg);
		
		List<String> expected = new ArrayList<String>();
		expected.add("Push");
		expected.add("Pull");
		
		Assert.assertFalse(validator.getUpperCaseMethods().isEmpty()); 
		Assert.assertEquals(expected, validator.getUpperCaseMethods());		
	}
}
