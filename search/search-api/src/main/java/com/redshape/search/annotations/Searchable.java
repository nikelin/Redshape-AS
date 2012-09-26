package com.redshape.search.annotations;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 2:44:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Inherited
public @interface Searchable {

    String name();

}
