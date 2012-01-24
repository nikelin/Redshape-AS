package com.redshape.persistence.dao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 2:46:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IndexField {
    /**
     * Name of field in index
     *
     * @return
     */
    String value();
}



