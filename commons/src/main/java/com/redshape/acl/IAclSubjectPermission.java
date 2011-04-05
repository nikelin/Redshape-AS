package com.redshape.acl;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 6:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclSubjectPermission<P extends IAclPermission, T extends IAclSubject<?>, V extends IAclObject<?>> {

    public void setSubject( T subject );

    public T getSubject();

    public void setObject( V object );

    public V getObject();

    public void setPermission( P permission );

    public P getPermission();

    public void markDenied();

    public void markAllowed();

}
