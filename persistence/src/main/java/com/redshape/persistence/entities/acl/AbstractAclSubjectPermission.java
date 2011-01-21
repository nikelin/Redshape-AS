package com.redshape.persistence.entities.acl;

import com.redshape.acl.IAclObject;
import com.redshape.acl.IAclPermission;
import com.redshape.acl.IAclSubject;
import com.redshape.acl.IAclSubjectPermission;
import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.persistence.entities.IEntity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 5:45:28 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractAclSubjectPermission<T extends IEntity & IAclSubjectPermission,
                                                   V extends IEntity & IAclSubject,
                                                   Q extends IEntity & IAclObject>
                    extends AbstractEntity implements IAclSubjectPermission<AclPermission, V, Q> {

    @ManyToOne
    @JoinColumn( name = "permission_id" )
    public AclPermission permission;

    @Basic
    public Boolean status;

    public void markDenied() {
        this.status = false;
    }

    public void markAllowed() {
        this.status = true;
    }

    @Override
    public AclPermission getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission( AclPermission permission ) {
        this.permission = permission;
    }
    
}
