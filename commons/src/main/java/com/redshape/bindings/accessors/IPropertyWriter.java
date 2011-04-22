package com.redshape.bindings.accessors;

import java.lang.reflect.AccessibleObject;

public interface IPropertyWriter extends IPropertyAccessor {

	public AccessibleObject getObject();

	public void write( Object context, Object value ) throws AccessException;
	
}
