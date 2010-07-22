package com.vio.search.annotations;

import com.vio.search.ISearchable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:08:12 PM
 * To change this template use File | Settings | File Templates.
 */
public @interface Collector {

    Class<? extends ISearchable> entityType();

}
