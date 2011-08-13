package com.redshape.ascript.language.ast;

import com.redshape.ascript.language.ast.AbstractSyntaxTreeNode;

public class FunctionScopeTreeNode extends AbstractSyntaxTreeNode
								   implements  IScopeTreeNode {

	@Override
	public boolean isComposed() {
		return false;
	}

	@Override
	public String toString() {
		return "{Function End}";
	}
	
}
