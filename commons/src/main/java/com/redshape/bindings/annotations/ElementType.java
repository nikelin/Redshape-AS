package com.redshape.bindings.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redshape.bindings.types.CollectionType;

@Retention( RetentionPolicy.RUNTIME )
@Target({
	java.lang.annotation.ElementType.FIELD,
	java.lang.annotation.ElementType.METHOD
})
public @interface ElementType {
	
	public Class<?> value();
	
	public CollectionType type() default CollectionType.LIST;
	
}
