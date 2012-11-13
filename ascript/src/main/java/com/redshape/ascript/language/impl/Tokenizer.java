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

package com.redshape.ascript.language.impl;


import com.redshape.ascript.language.IToken;
import com.redshape.ascript.language.ITokenizer;
import com.redshape.ascript.language.TokenType;
import com.redshape.ascript.language.tokens.Token;

import java.util.regex.Pattern;

public class Tokenizer implements ITokenizer {
	private static final Pattern alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9_\\-\\=\\+\\*" +
			"\\/\\^%&\\|\\~\\!\\<\\>\\.]+$");

	@Override
	public IToken process( char input ) {
		switch ( input ) {
		case '"':
			return new Token( TokenType.T_STRING );
		case ':':
			return new Token( TokenType.T_COLON );
		case '(':
			return new Token( TokenType.T_CONTEXT_START );
		case ')':
			return new Token( TokenType.T_CONTEXT_END );
		case ' ':
			return new Token( TokenType.T_SEPARATOR );
		case '#':
			return new Token( TokenType.T_SHARP );
		case ';':
			return new Token( TokenType.T_END );
		case '.':
			return new Token( TokenType.T_PATH );
		default:
			if ( alphaNumericPattern.matcher( String.valueOf(input) ).find() ) {
				return new Token( TokenType.T_LITERAL, input );
			} else {
				return new Token( TokenType.T_UNKNOWN );
			}
		}
	}
	
}
