package com.redshape.utils.validators.annotations;

import java.lang.annotation.*;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.bindings.annotations.validation
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

}
