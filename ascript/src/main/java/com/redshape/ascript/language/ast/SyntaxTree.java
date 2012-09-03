package com.redshape.ascript.language.ast;

import com.redshape.ascript.language.ast.IComposedSyntaxTreeNode;
import com.redshape.ascript.language.ast.ISyntaxTree;

public class SyntaxTree implements ISyntaxTree {
	private IComposedSyntaxTreeNode root;
	
	public SyntaxTree() {
		this.root = new ScopeTreeNode();
	}

	@Override
	public void insert( ISyntaxTreeNode node ) {
		this.insert( this.getRoot(), node );
	}

	@Override
	public void insert( IComposedSyntaxTreeNode parent, ISyntaxTreeNode node ) {
		parent.addArgument( node );
	}

	@Override
	public IComposedSyntaxTreeNode getRoot() {
		return this.root;
	}

	@Override
	public String toString() {
		return "{\n Tree: \n" + String.valueOf( this.getRoot() ) + "\n}";
	}

}
