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


import com.redshape.ascript.language.ast.AbstractComposedSyntaxTreeNode;

public class FunctionTreeNode extends AbstractComposedSyntaxTreeNode {
	private static final Class<? extends IScopeTreeNode>[] RELATED
				= new Class[] { FunctionScopeTreeNode.class };
	private String name;

	public FunctionTreeNode( String name ) {
		this.name = name;
	}

	@Override
	public Class<? extends IScopeTreeNode>[] getRelatedScopes() {
		return RELATED;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean isComposed() {
		return true;
	}

	@Override
	public String toString() {
		return "{ Function with name='" + this.getName() + "' Arguments:" + String.valueOf( this.getArguments() ) + " }";
	}
	
}
