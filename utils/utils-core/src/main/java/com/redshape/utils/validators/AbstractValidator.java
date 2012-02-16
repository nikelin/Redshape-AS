package com.redshape.utils.validators;

import java.util.HashMap;
import java.util.Map;

import com.redshape.utils.validators.result.IValidationResult;

public abstract class AbstractValidator<T, R extends IValidationResult> implements IValidator<T, R> {
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	@Override
	public void setAttribute(String name, Object value) {
		if ( name == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		this.attributes.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	protected <V> V getAttribute( String name ) {
		return (V) this.attributes.get( name );
	}
	
	protected Map<String, Object> getAttributes() {
		return this.attributes;
	}
	
}
