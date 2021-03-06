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

package com.redshape.search.lucene.query.transformers;

import com.redshape.search.lucene.engines.lucene.LuceneEngine;
import com.redshape.search.query.terms.*;
import com.redshape.search.query.terms.impl.ToTerm;
import com.redshape.search.query.transformers.IQueryTransformer;
import com.redshape.search.query.transformers.QueryTransformationException;
import com.redshape.utils.SimpleStringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 12:12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class LuceneQueryTransformer implements IQueryTransformer {

	@Override
	public Query transform( ISearchTerm term ) throws QueryTransformationException {
		try {
			return this.createQueryParser().parse( this._transform(term) );
		} catch ( Throwable e ) {
			throw new QueryTransformationException( e.getMessage(), e );
		}
	}

	protected String _transform( ISearchTerm term ) throws QueryTransformationException {
		try {
			/**
			 * if processor for term class exists call
			 * him first
			 */
			try {
				Method m = this.getClass().getMethod("_transform", term.getClass() );
				return (String) m.invoke(this, term);
			} catch ( Throwable e ) { }

			/**
			 * ...if not, call conformable instead (if exists)
			 */
			for ( Method method : this.getClass().getDeclaredMethods() ) {
				if ( method.getName().startsWith("_")
						&& method.getParameterTypes().length > 0
						&& !method.getParameterTypes()[0].equals( ISearchTerm.class )
						&& method.getParameterTypes()[0].isAssignableFrom( term.getClass() ) ) {
					return (String) method.invoke( this, term );
				}
			}

			throw new QueryTransformationException("Term " + term.getClass().getCanonicalName()
					+ " not supported by current engine");
		} catch ( QueryTransformationException e ) {
			throw e;
		} catch ( Throwable e ) {
			throw new QueryTransformationException( e.getMessage(), e );
		}
	}

	protected String _transform( IFieldTerm term ) throws QueryTransformationException {
		StringBuilder builder = new StringBuilder();
		builder.append( term.getField() )
				.append( ":" )
				.append( this._transform( term.getTerm() ) );
		return builder.toString();
	}

	protected String _transform( IGroupingTerm term ) throws QueryTransformationException {
		StringBuilder builder = new StringBuilder();
		builder.append("( ");
		int i = 0;
		for ( ISearchTerm item : term.getList() ) {
			builder.append( this._transform(item) )
					.append(" ");
			if ( i++ != term.getList().length - 1 ) {
				builder.append( this._transform(term.getOperation()));
			}
		}
		builder.append(" )");

		return builder.toString();
	}

	protected String _transform( ToTerm term ) throws QueryTransformationException {
		StringBuilder builder = new StringBuilder();

		builder.append(" ")
				.append(term.getType().openSymbol())
				.append( this._transform( term.getLeft() ) )
				.append( this._transform( term.getOperation() ) )
				.append( this._transform( term.getRight() ) )
				.append( term.getType().closingSymbol() )
				.append(" ");

		return builder.toString();
	}

	protected String _transform( IBinaryTerm term ) throws QueryTransformationException {
		StringBuilder builder = new StringBuilder();

		builder.append( this._transform( term.getLeft() ) );
		builder.append( this._transform( term.getOperation() ) );
		builder.append( this._transform( term.getRight() ) )
				.append(" ");

		return builder.toString();
	}

	protected String _transform( IUnaryTerm term ) throws QueryTransformationException {
		StringBuilder builder = new StringBuilder();

		builder.append( this._transform( term.getOperation() ) );
		builder.append( this._transform( term.getTerm() ) )
				.append(" ");

		return builder.toString();
	}

	protected String _transform( IScalarTerm term ) {
		return "\""
				.concat(
						SimpleStringUtils.escape(String.valueOf(term.getValue()),
                                LuceneEngine.ESCAPE_SEQUENCES)
				)
				.concat( "\"" );
	}

	protected String _transform( Operation operation ) throws QueryTransformationException {
		String result;
		switch ( operation ) {
			case NOT:
				result = "-";
				break;
			case EQUALS:
				result = "=";
				break;
			case AND:
			case TO:
			case OR:
				result = operation.name();
				break;
			default:
				throw new QueryTransformationException("Unsupported operation");
		}

		return " ".concat( result.concat(" ") );
	}

	protected QueryParser createQueryParser() {
		return new QueryParser(LuceneEngine.VERSION, "", this.createAnalyzer() );
	}

	protected Analyzer createAnalyzer() {
		return new StandardAnalyzer( LuceneEngine.VERSION );
	}

}
