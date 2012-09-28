package com.redshape.utils.beans.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

import com.redshape.utils.beans.bindings.types.BindableType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
@Inherited
public @interface Bindable {
	
	public String id() default "";
	
	public BindableType type() default BindableType.NONE;
	
	public Class<?>[] targetType() default {};
	
	public String name() default "";

    public BindableAttributes[] attributes() default {};
	
}
