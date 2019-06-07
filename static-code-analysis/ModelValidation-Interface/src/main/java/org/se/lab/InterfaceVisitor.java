package org.se.lab;

import org.se.lab.ast.*;


public interface InterfaceVisitor
{
	void visit(MPackage pkg);
	void visit(MInterface iface);
	void visit(MOperation op);
	void visit(MParameter par);
	void visit(MType type);
}
