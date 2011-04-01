package com.redshape.bindings.accessors;

public interface IPropertyWriter extends IPropertyAccessor {
	
	public void write( Object context, Object value ) throws AccessException;
	
}
