package com.redshape.utils.beans.bindings.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redshape.utils.beans.bindings.types.CollectionType;

@Retention( RetentionPolicy.RUNTIME )
@Target({
	java.lang.annotation.ElementType.FIELD,
	java.lang.annotation.ElementType.METHOD
})
@Inherited
public @interface ElementType {
	
	public Class<?> value();
	
	public CollectionType type() default CollectionType.LIST;
	
}
