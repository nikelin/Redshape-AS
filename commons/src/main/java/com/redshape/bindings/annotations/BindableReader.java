package com.redshape.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD, ElementType.TYPE } )
@Inherited
public @interface BindableReader {

	public AccessorType type() default AccessorType.METHOD;
	
	public String name();
	
}
