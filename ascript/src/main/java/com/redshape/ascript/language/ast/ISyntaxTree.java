package com.redshape.ascript.language.ast;

public interface ISyntaxTree {

	public void insert( ISyntaxTreeNode node);

	public void insert(IComposedSyntaxTreeNode parent, ISyntaxTreeNode node);

	public ISyntaxTreeNode getRoot();
	
}
