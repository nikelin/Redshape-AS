package com.redshape.persistence.entities.acl;

import com.redshape.acl.*;
import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.persistence.entities.IEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 5:39:22 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class AclObject<T extends IEntity & IAclObject> extends AbstractEntity<T> implements IAclObject<AclPermission> {
    @ManyToMany
    @JoinTable(
        name = "acl_objects_permissions",
        joinColumns = @JoinColumn(
            name = "permission_id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "object_id"
        )
    )
    private Set<AclPermission> permissions = new HashSet();

    public Collection<AclPermission> getPermissions()  {
        return this.permissions;
    }

    public boolean hasPermission( AclPermission permission ) {
        return this.permissions.contains( permission );
    }

    public void addPermission( AclPermission permission ) {
        this.permissions.add( permission );
    }

}
