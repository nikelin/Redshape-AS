package com.redshape.bindings.accessors;

public interface IPropertyReader extends IPropertyAccessor {

	public <V> V read( Object context ) throws AccessException;
	
}
