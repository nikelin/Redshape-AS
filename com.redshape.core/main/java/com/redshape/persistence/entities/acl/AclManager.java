package com.redshape.persistence.entities.acl;

import com.redshape.acl.*;
import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.managers.IManager;
import com.redshape.persistence.managers.ManagerException;
import com.redshape.persistence.managers.ManagersFactory;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 20, 2010
 * Time: 4:50:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AclManager<T extends IAclSubjectPermission & IEntity> implements IAclManager<T> {

    @Override
    synchronized public void grantAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        final Class<T> subjectPermissionClass = subject.getAclPermissionClass();
        if ( subjectPermissionClass == null ) {
            throw new AclException();
        }

        try {
            T permissionEntity = this.getSubjectPermission( subject, object, permission );
            if ( permissionEntity == null ) {
                permissionEntity = this.createSubjectPermission( subject, object, permission );
            }

            permissionEntity.markAllowed();
            permissionEntity.save();
        } catch ( ManagerException e ) {
            throw new AclException("Internal exception");
        }
    }

    @Override
    synchronized public void revokeAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        Class<T> subjectPermissionClass = subject.getAclPermissionClass();
        if ( subjectPermissionClass == null ) {
            throw new AclException();
        }

        try {
            T permissionEntity = this.getSubjectPermission( subject, object, permission );
            if ( permissionEntity == null ) {
                permissionEntity = this.createSubjectPermission( subject, object, permission );
            }

            permissionEntity.markDenied();
            permissionEntity.save();
        } catch ( ManagerException e ) {
            throw new AclException();
        }
    }

    @Override
    public void denySubject( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException {
        for ( IAclPermission permission : object.getPermissions() ) {
            this.grantAccess( subject, object, permission );
        }
    }

    @Override
    public Collection<T> getSubjectPermissions( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException {
        try {
            return ManagersFactory.getDefault()
                           .getForEntity( subject.getAclPermissionClass() )
                           .findMatches(
                                   new String[] { "subject.id", "object.id" },
                                   new Object[] { subject.getId(), object.getId() }
                           );
        } catch ( ManagerException e ) {
            throw new AclException();
        }
    }

    @Override
    public T getSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        try {
            return (T) ManagersFactory.getDefault()
                           .getForEntity( subject.getAclPermissionClass() )
                           .findOneMatched(
                                   new String[] { "subject.id", "object.id" },
                                   new Object[] { subject.getId(), object.getId() }
                           );
        } catch ( ManagerException e ) {
            throw new AclException();
        }
    }

    @Override
    public T createSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        try {
            T subjectPermission = subject.getAclPermissionClass().newInstance();
            subjectPermission.setObject( object );
            subjectPermission.setSubject( subject );
            subjectPermission.setPermission( permission );

            return subjectPermission;
        } catch ( Throwable e ) {
            throw new AclException();
        }
    }

    /**
     *
     * @param permission
     * @return
     */
    public IAclPermission createPermission( String permission ) {
        return new AclPermission(permission);
    }

}
