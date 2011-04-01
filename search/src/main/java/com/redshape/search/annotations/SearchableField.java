package com.redshape.search.annotations;

import com.redshape.search.index.IndexingType;

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
public @interface SearchableField {
    /**
     * Name of field in index
     * @return
     */
    String name();

    /**
     * Type for field contents
     * @return
     */
    IndexingType type() default IndexingType.TEXT;

    /**
<<<<<<< HEAD
<<<<<<< HEAD
     * Store or not this field in search index
=======
     * Store or not this field in com.redshape.search index
>>>>>>> refs/heads/2.0.0A
=======
     * Store or not this field in com.redshape.search index
>>>>>>> refs/heads/2.0.0A
     * @return
     */
    boolean stored() default true;

    /**
     * Process lexical analysis on current field
     * @return
     */
    boolean analyzable() default true;

    /**
     * Store value as a bytes array
     * @return
     */
    boolean binary() default true;

    /**
     * Field value for result scoring
     * @return
     */
    int rank() default 0;

}
