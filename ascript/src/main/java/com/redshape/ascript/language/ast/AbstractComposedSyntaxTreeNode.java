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

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikelin
 * @date 19/04/11
 * @package com.redshape.ascript.language.ast
 */
public abstract class AbstractComposedSyntaxTreeNode
			extends AbstractSyntaxTreeNode
			implements IComposedSyntaxTreeNode {
	private List<ISyntaxTreeNode> arguments = new ArrayList<ISyntaxTreeNode>();

	@Override
	public ISyntaxTreeNode shiftArgument() {
		ISyntaxTreeNode result = this.arguments.get(0);
		this.arguments = this.arguments.subList( 1, this.arguments.size() );
		return result;
	}

	@Override
	public void removeArgument( ISyntaxTreeNode node ) {
		this.arguments.remove(node);
	}

	@Override
	public void addArgument(ISyntaxTreeNode node) {
		this.arguments.add(node);
	}

	@Override
	public List<ISyntaxTreeNode> getArguments() {
		return this.arguments;
	}

}
