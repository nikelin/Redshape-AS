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

public class LiteralTreeNode extends AbstractSyntaxTreeNode {
	private Object value;
	
	public LiteralTreeNode( Object value ) {
		this.value = value;
	}
	
	@Override
	public boolean isComposed() {
		return false;
	}

	public boolean isBoolean() {
		return this.value instanceof Boolean;
	}

	public boolean isNumeric() {
		return this.value instanceof Number;
	}

	public boolean isString() {
		return this.value instanceof Number;
	}

	public Object getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "{Literal: " + this.getValue() + "}";
	}
	
}
