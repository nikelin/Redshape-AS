package com.redshape.utils.beans.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD, ElementType.TYPE } )
@Inherited
public @interface BindableWriter {
	
	public String name();	
	
}
