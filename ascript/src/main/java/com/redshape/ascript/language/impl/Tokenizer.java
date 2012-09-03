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
