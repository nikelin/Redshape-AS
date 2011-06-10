package com.redshape.validators.annotations;

import java.lang.annotation.*;

/**
 * Annotation which needs to provide ability of marking target entity
 * field or accessor which must to be treated as email.
 * 
 * Often used with a @see com.redshape.validators.BeansValidator.
 * 
 * @see com.redshape.validators.annotations.Validator
 * @see com.redshape.validators.annotations.Validators
 * 
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.bindings.annotations.validation
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
}
