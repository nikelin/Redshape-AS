package com.redshape.ascript.language.ast;

import com.redshape.ascript.language.ast.AbstractComposedSyntaxTreeNode;

/**
 * @author nikelin
 */
public class ScopeTreeNode extends AbstractComposedSyntaxTreeNode implements IScopeTreeNode{

	@Override
	public boolean isComposed() {
		return false;
	}

	@Override
	public Class<? extends IScopeTreeNode>[] getRelatedScopes() {
		throw new UnsupportedOperationException("Operation not supports on this type");
	}

	@Override
	public String toString() {
		return "{Global Scope}";
	}

}
