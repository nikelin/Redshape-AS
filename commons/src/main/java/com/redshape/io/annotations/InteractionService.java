package com.redshape.io.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redshape.io.PlatformType;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface InteractionService {

    public String id();

    public PlatformType[] platforms();

    public RequiredPort[] ports() default {};

}
