package com.redshape.annotations.dev;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Oct 4, 2010
 * Time: 1:23:52 PM
 * To change this template use File | Settings | File Templates.
 */
public @interface Refactor {

    public String reason();

    public int importance() default 0;
    
}
