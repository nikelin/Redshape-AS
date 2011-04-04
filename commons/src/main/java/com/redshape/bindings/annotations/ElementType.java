package com.redshape.bindings.annotations;

import com.redshape.bindings.types.CollectionType;

public @interface ElementType {
	
	public Class<?> value();
	
	public CollectionType type() default CollectionType.LIST;
	
}
