package com.redshape.search.annotations;

<<<<<<< HEAD
<<<<<<< HEAD
import com.redshape.search.ISearchable;

=======
>>>>>>> refs/heads/2.0.0A
=======
>>>>>>> refs/heads/2.0.0A
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:08:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Collector {

    Class<?> entityType();

}
