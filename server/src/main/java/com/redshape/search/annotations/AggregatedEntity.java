package com.redshape.search.annotations;

import com.redshape.search.ISearchable;
import com.redshape.search.index.AggregationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:31:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AggregatedEntity {

    public Class<? extends ISearchable> targetEntity() default ISearchable.class;

    public AggregationType type();

    public SearchableField[] include() default {};

    public SearchableField[] exclude() default {};

}
