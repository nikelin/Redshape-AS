package com.redshape.validators.annotations;

import java.lang.annotation.*;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.bindings.annotations.validation
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

	public int max();

	public int min();

}
