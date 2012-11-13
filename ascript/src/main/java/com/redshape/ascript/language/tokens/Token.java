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

package com.redshape.ascript.language.tokens;

import com.redshape.ascript.language.IToken;
import com.redshape.ascript.language.TokenType;

public class Token implements IToken {
	
	private TokenType type;
	private Object value;
	
	public Token( TokenType type ) {
		this(type, null);
	}
	
	public Token( TokenType type, Object value ) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public TokenType getType() {
		return this.type;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

}
