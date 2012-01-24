package com.redshape.persistence.dao.annotations;

/**
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 2:44:30 PM
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Index {
    public IndexField[] value();
}



