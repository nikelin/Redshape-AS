package com.redshape.io.annotations;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public @interface RequiredPort {

    public int value();

    public String[] protocols() default "tcp";

}
