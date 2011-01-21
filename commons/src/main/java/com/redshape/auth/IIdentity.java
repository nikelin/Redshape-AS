package com.redshape.auth;

import com.redshape.features.IFeatureInteractor;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IIdentity<T> extends IFeatureInteractor<T> {

    public Long getLastAccessTime();

    public void setLastAccessTime( Long time );

}
