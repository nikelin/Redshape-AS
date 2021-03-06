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

package com.redshape.ascript.context;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.items.*;
import com.redshape.utils.Function;
import com.redshape.utils.IFunction;
import com.redshape.utils.ILambda;
import com.redshape.utils.SimpleStringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class EvaluationContext implements IEvaluationContext {
	private static final Logger log = Logger.getLogger( EvaluationContext.class );

	private Map<String, String> functionAliases = new HashMap<String, String>();

	private Map<String, IEvaluationContextItem> items 
						= new HashMap<String, IEvaluationContextItem>();

	@Override
	public Integer getObjectsCount() {
		return this.items.size();
	}

	@Override
	public void reset() {
		this.items.clear();
		this.functionAliases.clear();
	}

    @Override
    public Map<String, FunctionItem> listFunctions( IEvaluationContext context ) throws EvaluationException {
        Map<String, FunctionItem> items = new HashMap<String, FunctionItem>();
        for ( String key : context.getItems().keySet() ) {
            IEvaluationContextItem item = context.getItems().get(key);
            if ( item instanceof FunctionItem ) {
                items.put( key, (FunctionItem) item );
            } else if ( item instanceof ContextItem ) {
                items.putAll( this.listFunctions( ( (ContextItem ) item ).<IEvaluationContext>getValue() ) );
            }
        }

        return items;
    }

    @Override
    public Map<String, IEvaluationContextItem> getItems() {
        return this.items;
    }

    @Override
    public Map<String, FunctionItem> listFunctions() throws EvaluationException {
        return this.listFunctions( this );
    }

    @Override
	public void exportValue(String name, Object value) throws EvaluationException {
		IEvaluationContext context = this;

		Queue<String> path = this.createPath( name );
		String actualName = name;
		if ( path.size() > 1 ) {
			do {
				IEvaluationContextItem targetContext = context.get( path.peek() );
				if ( targetContext == null ) {
					context.exportContext( path.poll(), context = new EvaluationContext() );
				} else {
					if ( context instanceof ContextItem ) {
						context = targetContext.getValue();
						path.poll();
					} else {
						throw new IllegalArgumentException("Ambiguos context name!");
					}
				}
			} while ( path.size() != 1 );

			actualName = path.peek();

			context.exportValue( actualName, value );
		} else {
			this.items.put( actualName, new ValueItem(value) );
		}
	}

	@Override
	public void exportFunctionAlias(String originalName, String alias) throws EvaluationException {
		this.functionAliases.put( alias, originalName );
	}

	@Override
	public void exportMap(String name, Map<String, ?> values) {
		this.items.put( name, new MapItem(values) );
	}

	@Override
	public void exportBean(String name, Class<?> description, Object value) {
		this.items.put( name, new BeanItem(description, value) );
	}

	@Override
	public void exportContext( String name, IEvaluationContext context ) {
		this.items.put( name, new ContextItem( context ) );
	}

	@Override
	public void exportClass( String name, Class<?> clazz ) throws EvaluationException {
		this.items.put( name, new ClassContext( clazz ) );
	}

	@Override
	public void exportFunction( String name, ILambda<?> context ) throws EvaluationException {
		this.items.put(name, new FunctionItem( context ) );
	}
	
	@Override
	public IEvaluationContextItem get(String name) {
		return this.items.get(name);
	}

	@Override
	public <T> ILambda<T> resolveFunction( String name, int argumentsCount )
		throws EvaluationException {
		return this.resolveFunction( name, argumentsCount, new Class[] {} );
	}

	@Override
	public <T> ILambda<T> resolveFunction(String name, int argumentsCount, Class<?>[] types )
		throws EvaluationException {
		String actualName = this.functionAliases.get(name);
		if ( actualName == null ) {
			actualName = name;
		}

		Queue<String> path = this.createPath(actualName);
		if ( path.isEmpty() ) {
			return null;
		}
		
		log.info("Trying to find method on path: " + path );
		
		return this.resolveMethod( this.get( path.poll() ), argumentsCount, path, types );
	}

	protected <T> ILambda<T> resolveMethod( IEvaluationContextItem context,
			int argumentsCount, Queue<String> path, Class<?>[] types ) throws EvaluationException {
		log.info("Method resolving path node: " + path.peek() );
		log.info("Context item type: " + context.getClass().getName() );
		if ( context instanceof FunctionItem ) {
			return context.getValue();
		} else if ( context instanceof ValueItem ) {
			if ( context.getValue() instanceof IFunction ) {
				return context.getValue();
			} else if ( path.isEmpty() ) {
				throw new EvaluationException("Requested object is not lambda type");
			}
		}

		if ( path.size() == 1) {
			log.info("Resolving method " + path.peek() );
            if ( context.getValue() instanceof ILambda ) {
			    return context.getMethod( path.poll(), argumentsCount, types);
            } else {
                return context.getValue();
            }
		} else {
			return this.resolveMethod(
					this.createContextItem(context.<Object>getValue(path.poll()), false), argumentsCount, path, types);
		}
	}

	@Override
	public void unexportValue( String name ) {
		this.items.remove(name);
	}

	@Override
	public <V> V resolve(String name) throws EvaluationException {
		Queue<String> path = this.createPath(name);
		if ( path.isEmpty() ) {
			return null;
		}
		
		log.info("Resolving field " + name + " on path : " + path);
		
		IEvaluationContextItem first = this.get( path.poll() );
		if ( first == null ) {
			return null;
		}

		log.info("Resolving context: " + first.getClass().getName() );
		if ( path.size() == 0 ) {
			return first.<V>getValue();
		} else {
			return this.<V>resolve( first, path );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <V> V resolve( IEvaluationContextItem context, Queue<String> path ) throws EvaluationException {
		log.info("Path node: " + path.peek() );
		Object object = context.getValue( path.poll() );
		if ( object == null ) {
			return null;
		}
		
		if ( path.isEmpty() ) {
			if ( object instanceof IEvaluationContextItem ) {
				return (V) ( (IEvaluationContextItem) object ).getValue();
			} else {
				return (V) object;
			}
		} else {
			IEvaluationContextItem item;
			if ( object instanceof IEvaluationContextItem ) {
				item = (IEvaluationContextItem) object;
			} else {
				item = this.createContextItem( object, path.size() > 1 );
			}

			return this.<V>resolve( item, path );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected IEvaluationContextItem createContextItem( Object object, boolean scalar ) {
		if ( object instanceof IEvaluationContextItem) {
			return (IEvaluationContextItem) object;
		}

		if ( scalar ) {
			return new ValueItem(object);
		} else if ( object instanceof Map ) {
			return new MapItem( (Map<String,?>) object );
		} else if ( object instanceof IEvaluationContext ) {
			return new ContextItem( (IEvaluationContext) object );
		} else {
			return new BeanItem(object.getClass(), object);
		}
	}

	protected Queue<String> createPath( String path ) {
		Queue<String> result = new LinkedBlockingQueue<String>();

		String actualPath = null;
		List<String> tokens = Arrays.asList( path.split("\\.") );
		for ( int i = 0; i < tokens.size(); i++ ) {
			String subjoin = SimpleStringUtils.join(tokens.subList(0, tokens.size() - i), ".");
			if ( this.get( subjoin ) != null ) {
				if ( i > 0 ) {
					actualPath = SimpleStringUtils.join(tokens.subList(tokens.size() - i, tokens.size()), ".");
				}

				result.add( subjoin );
				break;
			}
		}

		if ( actualPath != null && !actualPath.isEmpty() ) {
			result.addAll( Arrays.asList( actualPath.split("\\.") ) );
		}

		return result;
	}
	
}
