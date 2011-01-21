package com.redshape.search.annotations;

import com.redshape.search.serializers.ISerializer;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 5:12:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SearchableFieldSerializer {

    Class<? extends ISerializer> serializer();

}
