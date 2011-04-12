package com.redshape.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention( RetentionPolicy.RUNTIME )
@Inherited
public @interface MapValue {
	
	public String name() default "";
	
	public Class<?> value();
	
}
