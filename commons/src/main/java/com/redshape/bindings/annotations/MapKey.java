package com.redshape.bindings.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention( RetentionPolicy.RUNTIME )
public @interface MapKey {
	
	public String name() default "";
	
	public Class<?> value();
	
}
