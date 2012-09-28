package com.redshape.persistence.entities;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/8/12
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Inherited
@Target({ FIELD, PARAMETER, METHOD })
@Retention(RUNTIME)
public @interface DtoIgnore {
}
