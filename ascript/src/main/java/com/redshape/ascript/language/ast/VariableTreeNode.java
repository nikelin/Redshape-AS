package com.redshape.ascript.language.ast;

import com.redshape.ascript.language.ast.AbstractSyntaxTreeNode;

public class VariableTreeNode extends AbstractSyntaxTreeNode {
	private String name;
	
	public VariableTreeNode( String name ) {
		this.name = name;
	}
	
	@Override
	public boolean isComposed() {
		return false;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "{ Variable: " + this.getName() + "}";
	}
	
}
