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

import com.redshape.ascript.language.DeclarationType;

/**
 * @author nikelin
 * @date 19/04/11
 * @package com.redshape.ascript.language.ast
 */
public abstract class AbstractSyntaxTreeNode implements ISyntaxTreeNode {

	@Override
	public boolean isComposed() {
		return this instanceof IComposedSyntaxTreeNode;
	}

	@Override
	public boolean isList() {
		return this.isDeclaration()
				&& ( (DeclarationTreeNode) this ).getType().equals( DeclarationType.LIST );
	}

	@Override
	public boolean isMap() {
		return this.isDeclaration()
				&& ( (DeclarationTreeNode) this ).getType().equals( DeclarationType.MAP );
	}

	@Override
	public boolean isDeclaration() {
		return this instanceof DeclarationTreeNode;
	}

	@Override
	public IComposedSyntaxTreeNode asComposedNode() {
		if ( !this.isComposed() ) {
			throw new UnsupportedOperationException("There is no conversion to composed node");
		}

		return (IComposedSyntaxTreeNode) this;
	}
}
