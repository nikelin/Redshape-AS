package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContextItem;

import java.lang.reflect.Method;
import java.util.Map;

public class MapItem implements IEvaluationContextItem {
	private Map<String,?> map;
	
	public MapItem( Map<String,?> map ) {
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(String name) throws EvaluationException {
		return (V) this.map.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue() throws EvaluationException {
		return (V) this.map;
	}
	
	@Override
	public Method getMethod( String name, int argumentsCount, Class<?>[] types ) throws EvaluationException {
		throw new IllegalArgumentException("Restricted operation on map");
	}
	
}
