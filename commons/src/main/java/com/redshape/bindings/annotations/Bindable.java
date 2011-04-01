package com.redshape.bindings.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redshape.bindings.types.BindableType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
public @interface Bindable {
	
	public String id() default "";
	
	public BindableType type() default BindableType.NONE;
	
	public String name() default "";
	
}
