package com.redshape.ascript.language;


import com.redshape.ascript.language.ast.ISyntaxTree;
import com.redshape.ascript.language.impl.LexerException;

public interface ILexer {

	public ISyntaxTree process() throws LexerException;
	
}
