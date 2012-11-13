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

package com.redshape.ascript.language;

public class GrammarRule implements IRule<TokenType> {
	private TokenType from;
	private TokenType to;
	
	public GrammarRule( TokenType from, TokenType to ) {
		this.from = from;
		this.to = to;
	}
	
	public TokenType getFrom() {
		return this.from;
	}
	
	public TokenType getTo() {
		return this.to;
	}
	
	@Override
	public String toString() {
		return this.from.toString() + "->" + this.to.toString();
	}
	
}
