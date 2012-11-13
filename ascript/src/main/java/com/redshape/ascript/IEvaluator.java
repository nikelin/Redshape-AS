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

package com.redshape.ascript;

import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.ascript.evaluation.EvaluationMode;
import com.redshape.utils.IResourcesLoader;

import java.io.IOException;
import java.util.Collection;

public interface IEvaluator {

    public Collection<String> getIncludes();

    public void addInclude(String path) throws IOException, EvaluationException;

    public void setIncludes(Collection<String> paths) throws IOException, EvaluationException;

	public IResourcesLoader getLoader();

	/**
	 * Reset all evaluation contexts which corresponds to given
	 * evaluator instance.
	 *
	 * @throws EvaluationException
	 */
	public void reset() throws EvaluationException;

	/**
	 * Evaluate expression and return evaluation result
	 * 
	 * @param expression
	 * @return
	 */
	public <T> T evaluate(String expression) throws EvaluationException;

	/**
	 * Load file contents, evaluates and return evaluation result
	 *
	 * @param path
	 * @return
	 */
	public <T> T evaluateFile(String path, EvaluationMode mode) throws EvaluationException;

    /**
	 *  Process expressions which embed in a text: "Afla Afla ${(counter a)} Afla#${(+ (counter a) 2)}"
	 *
	 * @param expression
	 * @return
	 * @throws EvaluationException
	 */
    public String processEmbed(String expression) throws EvaluationException;
	
	/**
	 * Register named context
	 * @param context
	 * @param name
	 */
	public void registerContext(IEvaluationContext context, String name) throws EvaluationException;
	
	/**
	 * Create new context and register its under root
	 * @param name
	 * @return
	 */
	public IEvaluationContext createContext(String name) throws EvaluationException;
	
	/**
	 * Return current evaluator root context
	 */
	public IEvaluationContext getRootContext();
	
}
