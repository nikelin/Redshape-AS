package com.redshape.utils.beans.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Inherited
public @interface BindableConstructor {
	
	public Class<?> type();
	
	public String name() default "";
	
}
