package com.redshape.servlet.core.restrictions;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 10/31/12
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MethodRestriction {

    public String[] value() default "";

}
