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
import com.redshape.ascript.context.items.FunctionItem;
import com.redshape.utils.IFunction;
import com.redshape.utils.ILambda;

import java.util.Map;

public interface IEvaluationContext {

	/**
	 * Get registered objects count
	 * @return
	 */
	public Integer getObjectsCount();

	/**
	 * Remove exported object from context
	 */
	public void unexportValue(String name);


	/**
	 * Remove all exported objects and aliases
	 */
	public void reset();

	/**
	 * Export simple variable to current context
	 * 
	 * @usage
	 * ${currentContext}.${name}
	 * 
	 * @param name
	 * @param value
	 */
	public void exportValue(String name, Object value) throws EvaluationException ;
	
	/**
	 * Export @see values with subcontext @see name creation
	 * 
	 * @usage
	 * ${currentContext}.${key from values}
	 * 
	 * @param name
	 * @param values
	 */
	public void exportMap(String name, Map<String, ?> values) throws EvaluationException ;
	
	/**
	 * Export object with subcontext creation
	 * 
	 * @usage
	 * ${currentContext}.${name}.${field from description}
	 * 
	 * @param name
	 * @param description
	 * @param value
	 */
	public void exportBean(String name, Class<?> description, Object value) throws EvaluationException ;
	
	public void exportFunction(String name, ILambda<?> fn) throws EvaluationException ;

	public void exportFunctionAlias(String originalName, String alias) throws EvaluationException;

	public void exportClass(String name, Class<?> clazz) throws EvaluationException;

	public void exportContext(String name, IEvaluationContext context) throws EvaluationException ;
	
	/**
	 * Get item registered in current context
	 * 
	 * @param name
	 * @return
	 */
	public IEvaluationContextItem get(String name);
	
	/**
	 * Get function from context by name
	 */
	public <T> ILambda<T> resolveFunction(String name, int argumentsCount)
			throws EvaluationException;

	public <T> ILambda<T> resolveFunction(String name, int argumentsCount, Class<?>[] types)
			throws EvaluationException;

	/**
	 * Resolve path to context child or subcontext element
	 */
	public <V> V resolve(String name) throws EvaluationException;

    /**
        * List all declared functions
        * @return
        */
    public Map<String, FunctionItem> listFunctions()  throws EvaluationException;

    public Map<String, FunctionItem> listFunctions(IEvaluationContext context) throws EvaluationException;

    public Map<String, IEvaluationContextItem> getItems();

}
