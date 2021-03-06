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

package com.redshape.ascript.evaluation;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.IEvaluator;
import com.redshape.ascript.context.EvaluationContext;
import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.ascript.evaluation.functions.BeginFunction;
import com.redshape.ascript.evaluation.functions.arrays.CARFunction;
import com.redshape.ascript.evaluation.functions.arrays.CDRFunction;
import com.redshape.ascript.evaluation.functions.arrays.EmptyFunction;
import com.redshape.ascript.evaluation.functions.arrays.LengthFunction;
import com.redshape.ascript.evaluation.functions.binary.LeftShiftFunction;
import com.redshape.ascript.evaluation.functions.binary.RightShiftFunction;
import com.redshape.ascript.evaluation.functions.comparable.EqualsFunction;
import com.redshape.ascript.evaluation.functions.comparable.GreatThanFunction;
import com.redshape.ascript.evaluation.functions.comparable.LessThanFunction;
import com.redshape.ascript.evaluation.functions.comparable.NotEqualsFunction;
import com.redshape.ascript.evaluation.functions.language.ClassFunction;
import com.redshape.ascript.evaluation.functions.language.ListDeclaredFunction;
import com.redshape.ascript.evaluation.functions.language.NotDeclaredFunction;
import com.redshape.ascript.evaluation.functions.logical.AndFunction;
import com.redshape.ascript.evaluation.functions.logical.NotFunction;
import com.redshape.ascript.evaluation.functions.logical.OrFunction;
import com.redshape.ascript.evaluation.functions.math.*;
import com.redshape.ascript.evaluation.functions.strings.*;
import com.redshape.ascript.evaluation.functions.system.ListIncludesFunction;
import com.redshape.ascript.evaluation.functions.system.RootPathFunction;
import com.redshape.ascript.evaluation.functions.system.SearchPathFunction;
import com.redshape.ascript.evaluation.functions.utils.CounterFunction;
import com.redshape.ascript.language.ILexer;
import com.redshape.ascript.language.impl.Lexer;
import com.redshape.ascript.language.impl.LexerException;
import com.redshape.ascript.language.impl.Tokenizer;
import com.redshape.ascript.visitors.ISyntaxTreeVisitor;
import com.redshape.utils.IFunction;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.ResourcesLoader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionEvaluator implements IEvaluator {
	private static final Logger log = Logger.getLogger( ExpressionEvaluator.class );
	private IEvaluationContext rootContext;
	private ISyntaxTreeVisitor treeVisitor;
    private Collection<String> includes = new HashSet<String>();
	private IResourcesLoader loader;

    private static final Pattern embedPattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.MULTILINE);

	public ExpressionEvaluator() throws EvaluationException {
		this( new ResourcesLoader() );
	}

	public ExpressionEvaluator( IResourcesLoader loader ) throws EvaluationException {
		this( new EvaluationContext(), loader );
	}

	public ExpressionEvaluator( IEvaluationContext rootContext, IResourcesLoader loader ) throws EvaluationException {
		this( new SyntaxTreeProcessor(rootContext), rootContext, loader );
	}

	public ExpressionEvaluator( ISyntaxTreeVisitor visitor,
								IEvaluationContext rootContext,
								IResourcesLoader loader ) throws EvaluationException {
		this.rootContext = rootContext;
		this.treeVisitor = visitor;
		this.loader = loader;
		this.treeVisitor.setEvaluatorContext(this);

		this.init();
	}

	@Override
	public IResourcesLoader getLoader() {
		return this.loader;
	}

    public Collection<String> getIncludes() {
        return this.includes;
    }

    public void addInclude( String include ) throws IOException, EvaluationException {
        this.includes.add(include);
        this.evaluate( this.getLoader().loadData(include) );
    }

    public void setIncludes( Collection<String> includes ) throws IOException, EvaluationException {
        for ( String include : includes ) {
            this.addInclude( include );
        }
    }

	@Override
	public void reset() throws EvaluationException {
		this.getRootContext().reset();
		this.init();
	}

	protected void init() throws EvaluationException {
        /**
             * Language introspection functions
             */
        this.getRootContext().exportFunction("not-declared", new NotDeclaredFunction(this) );
        this.getRootContext().exportFunction("list-includes", new ListIncludesFunction(this) );
        this.getRootContext().exportFunction("list-declared", new ListDeclaredFunction(this) );
        this.getRootContext().exportFunction("class", new ClassFunction(this) );

        /**
              * String functions
              */
		this.getRootContext().exportFunction("split", new SplitFunction(this) );
        this.getRootContext().exportFunction("upper-case", new UpperCaseFunction(this) );
        this.getRootContext().exportFunction("lower-case", new LowerCaseFunction(this) );
        this.getRootContext().exportFunction("index-of", new IndexOfFunction(this) );
        this.getRootContext().exportFunction("concat", new ConcatFunction(this) );
        this.getRootContext().exportFunction("substring", new SubstringFunction(this) );

        /**
		 * System related functions
		 */
		this.getRootContext().exportFunction("root-path", new RootPathFunction(this) );
		this.getRootContext().exportFunction("search-path", new SearchPathFunction(this) );

		/**
		 * Math functions
		 */
        this.getRootContext().exportFunction("ceil", new CeilFunction(this) );
		this.getRootContext().exportFunction("+", new PlusFunction(this) );
		this.getRootContext().exportFunction("sum", new SumFunction(this) );
		this.getRootContext().exportFunction("-", new MinusFunction(this) );
		this.getRootContext().exportFunction("*", new MultiplyFunction(this) );
		this.getRootContext().exportFunction("%", new ModuloFunction(this) );
		this.getRootContext().exportFunctionAlias("%", "mod");
		this.getRootContext().exportFunction("^", new PowerFunction(this) );
		this.getRootContext().exportFunctionAlias("^", "pow");
		this.getRootContext().exportFunction("/", new DivideFunction(this) );
		this.getRootContext().exportFunctionAlias("/", "div");
        this.getRootContext().exportFunction("counter", new CounterFunction(this) );

		/**
		* Comparation functions
		 */
		this.getRootContext().exportFunction("=", new EqualsFunction(this) );
		this.getRootContext().exportFunctionAlias("=", "eq");
		this.getRootContext().exportFunction("!=", new NotEqualsFunction(this) );
		this.getRootContext().exportFunctionAlias("!=", "neq");
		this.getRootContext().exportFunction("<", new LessThanFunction(this) );
		this.getRootContext().exportFunctionAlias("<", "lt");
		this.getRootContext().exportFunction(">",  new GreatThanFunction(this) );
		this.getRootContext().exportFunctionAlias(">", "gt");

		/**
		 * Logic functions
		 */
		this.getRootContext().exportFunction("and", new AndFunction(this) );
		this.getRootContext().exportFunction("not", new NotFunction(this) );
		this.getRootContext().exportFunction("or", new OrFunction(this) );

		/**
		 * Binary operations
		 */
		this.getRootContext().exportFunction(">>", new RightShiftFunction(this) );
		this.getRootContext().exportFunctionAlias(">>", "rshift");
		this.getRootContext().exportFunction("<<", new LeftShiftFunction(this) );
		this.getRootContext().exportFunctionAlias("<<", "lshift");

		/**
		 * Control flow functions
		 */
		this.getRootContext().exportFunction("begin", new BeginFunction(this) );

		/**
		 * Arrays operations
		 */
		this.getRootContext().exportFunction("cdr", new CDRFunction(this) );
		this.getRootContext().exportFunction("car", new CARFunction(this) );
		this.getRootContext().exportFunction("length", new LengthFunction(this) );
		this.getRootContext().exportFunction("empty", new EmptyFunction(this) );

        for ( String path : this.getIncludes() ) {
            this.evaluate(path);
        }
	}
	
	@Override
	public IEvaluationContext getRootContext() {
		return this.rootContext;
	}

	public void setTreeVisitor( ISyntaxTreeVisitor visitor ) {
		this.treeVisitor = visitor;
	}

	public ISyntaxTreeVisitor getTreeVisitor() {
		return this.treeVisitor;
	}
	
	protected ILexer createLexer( String expression ) {
		return new Lexer( expression, new Tokenizer() );
	}

    @Override
    public String processEmbed(String expression) throws EvaluationException {
        Matcher matcher = embedPattern.matcher(expression);
        if ( !matcher.find() ) {
            return expression;
        }

        final StringBuilder builder = new StringBuilder();
        for ( int i = 0; i < matcher.groupCount(); i++ ) {
            builder.append( expression.substring(0, matcher.start(i+1) - 2 ) )
                .append(
                    String.valueOf(
                        this.<List<?>>evaluate( expression.substring( matcher.start(i+1), matcher.end(i+1) ) )
                        .get(0)
                    )
                )
                .append( expression.substring( matcher.end(i+1) + 1 ) );
            expression = builder.toString();
        }

        return expression;
    }

	@Override
	public <T> T evaluateFile(String path, EvaluationMode mode) throws EvaluationException {
		try {
			String data = this.getLoader().loadData(path);
			switch ( mode ) {
				case EMBED:
					return (T) this.processEmbed( data );
				case NORMAL:
					return this.evaluate( data );
				default:
					throw new EvaluationException("Unsupported evaluation mode");
			}
		} catch ( IOException e ) {
			throw new EvaluationException("I/O related exception", e );
		}
	}

	@Override
	public <T> T evaluate(String expression)
			throws EvaluationException {
		try {
			return this.getTreeVisitor().<T>visit(
				this.createLexer(expression).process()
			);
		} catch ( LexerException e  ) {
			throw new EvaluationException( e.getMessage(), e );
		}
	}
	
	@Override
	public void registerContext(IEvaluationContext context, String name)  throws EvaluationException {
		this.getRootContext().exportContext( name, context );
	}
	
	@Override
	public IEvaluationContext createContext( String name ) throws EvaluationException {
		IEvaluationContext context;
		this.registerContext( context = new EvaluationContext(), name );
		return context;
	}
	


}
