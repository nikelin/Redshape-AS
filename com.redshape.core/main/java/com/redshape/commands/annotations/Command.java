package com.redshape.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:30:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention( value = RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE)
public @interface Command {

    public String name();

    public String module();

    public String helpMessage() default "";

}
