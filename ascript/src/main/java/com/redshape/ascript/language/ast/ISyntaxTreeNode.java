package com.redshape.ascript.language.ast;

public interface ISyntaxTreeNode {

	public boolean isList();

	public boolean isMap();

	public boolean isDeclaration();

	public boolean isComposed();

	public IComposedSyntaxTreeNode asComposedNode();
	
}
