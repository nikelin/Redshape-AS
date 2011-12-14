package com.redshape.validators.annotations;

import com.redshape.validators.IValidator;

import java.lang.annotation.*;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.bindings.annotations.validation
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

	public Class<? extends IValidator> value();

	public boolean required() default true;

}
