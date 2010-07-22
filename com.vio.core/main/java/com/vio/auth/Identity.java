package com.vio.auth;

import com.vio.features.IFeatureInteractor;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Identity<T> extends IFeatureInteractor<T> {

    public Long getLastAccessTime();

    public void setLastAccessTime( Long time );

}
