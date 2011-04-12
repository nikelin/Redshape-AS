package com.redshape.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

import com.redshape.bindings.types.BindableType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
@Inherited
public @interface Bindable {
	
	public String id() default "";
	
	public BindableType type() default BindableType.NONE;
	
	public Class<?>[] targetType() default {};
	
	public String name() default "";
	
}
