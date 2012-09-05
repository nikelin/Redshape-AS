package com.redshape.utils.validators.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/22/12
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
