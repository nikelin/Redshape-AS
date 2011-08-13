package com.redshape.ascript.context;

import com.redshape.ascript.EvaluationException;

import java.lang.reflect.Method;

public interface IEvaluationContextItem {

	public Method getMethod(String name, int argumentsCount, Class<?>[] types) throws EvaluationException ;
	
	public <V> V getValue(String name) throws EvaluationException;
	
	public <V> V getValue() throws EvaluationException;
	
}
