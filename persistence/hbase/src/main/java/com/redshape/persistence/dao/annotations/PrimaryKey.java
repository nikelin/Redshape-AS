package com.redshape.persistence.dao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: cwiz
 * Date: 07.12.10
 * Time: 14:34
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrimaryKey {
    /**
     * Name of primary key
     *
     * @return
     */
    String value();
}