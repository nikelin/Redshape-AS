package com.redshape.ascript;

import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.utils.IResourcesLoader;
import org.apache.poi.hssf.record.formula.functions.T;

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
	public <T> T evaluateFile( String path ) throws EvaluationException;

    /**
	 *  Process expressions which embed in a text: "Afla Afla ${(counter a)} Afla#${(+ (counter a) 2)}"
	 *
	 * @param expression
	 * @param <T>
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
