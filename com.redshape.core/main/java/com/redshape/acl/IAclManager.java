package com.redshape.acl;

import com.redshape.persistence.entities.acl.AbstractAclSubjectPermission;
import com.redshape.persistence.entities.acl.AclPermission;

import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 20, 2010
 * Time: 4:21:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclManager<T extends IAclSubjectPermission> {

    public void grantAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;

    public void revokeAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;

    public  void denySubject( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException;

    public Collection<T> getSubjectPermissions( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException;

    public T getSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;
    
    /**
         * @param permission
         * @return
         */
    public IAclPermission createPermission( String permission );

    public T createSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;
}
