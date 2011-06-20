package com.redshape.auth;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IIdentity {

    public Date getLastAccessTime();

    public void setLastAccessTime( Date time );

}
