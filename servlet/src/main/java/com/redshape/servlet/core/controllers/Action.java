package com.redshape.servlet.core.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public @interface Action {

    public String name();

    public String controller();

    public String view() default "";

}
