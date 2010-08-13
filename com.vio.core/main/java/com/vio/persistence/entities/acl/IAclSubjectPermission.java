package com.vio.persistence.entities.acl;

import com.vio.persistence.entities.IEntity;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 6:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclSubjectPermission<T extends IAclSubject, V extends IAclObject> {

    public void setSubject( T subject );

    public T getSubject();

    public void setObject( V object );

    public V getObject();

    public Set<AclPermission> getPermissions();

    public void addPermission( AclPermission permission );

    public void removePermission( AclPermission permission );
}
