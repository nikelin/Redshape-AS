/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
