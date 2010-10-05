package com.redshape.payments.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 11, 2010
 * Time: 11:34:01 AM
 * To change this template use File | Settings | File Templates.
 */
@Retention( value = RetentionPolicy.RUNTIME )
public @interface PayableAspect {

    public String name() default "";

    public String description() default "";

}
