package com.redshape.persistence.entities.acl;

import com.redshape.acl.*;
import com.redshape.persistence.dao.ManagerException;
import com.redshape.persistence.entities.IEntity;

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

        T permissionEntity = this.getSubjectPermission( subject, object, permission );
        if ( permissionEntity == null ) {
            permissionEntity = this.createSubjectPermission( subject, object, permission );
        }

        permissionEntity.markAllowed();
            //@FIXME: due to dao refactoring
        	// permissionEntity.save();
    }

    @Override
    synchronized public void revokeAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        Class<T> subjectPermissionClass = subject.getAclPermissionClass();
        if ( subjectPermissionClass == null ) {
            throw new AclException();
        }

        T permissionEntity = this.getSubjectPermission( subject, object, permission );
        if ( permissionEntity == null ) {
            permissionEntity = this.createSubjectPermission( subject, object, permission );
        }

        permissionEntity.markDenied();
            //@FIXME: due to dao refactoring
//            permissionEntity.save();
    }

    @Override
    public void denySubject( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException {
        for ( IAclPermission permission : object.getPermissions() ) {
            this.grantAccess( subject, object, permission );
        }
    }

    @Override
    // @FIXME: due to dao refactoring
    public Collection<T> getSubjectPermissions( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException {
    	return null;
    }

    @Override
    // @FIXME: due to dao refactoring
    public T getSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException {
        return null;
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
