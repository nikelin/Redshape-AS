package com.redshape.bindings.accessors;

import java.lang.reflect.AccessibleObject;

public interface IPropertyReader extends IPropertyAccessor {

	public AccessibleObject getObject();

	public <V> V read( Object context ) throws AccessException;
	
}
