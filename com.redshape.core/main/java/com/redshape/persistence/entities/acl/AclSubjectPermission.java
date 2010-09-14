package com.redshape.persistence.entities.acl;

import com.redshape.persistence.entities.IEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 5:45:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity( name = "acl_subjects_permissions" )
public class AclSubjectPermission<T extends IAclSubject, V extends IAclObject & IEntity> implements IAclSubjectPermission<T,V> {

    @ManyToOne
    @JoinColumn( name = "acl_subject_id" )
    private T subject;

    @ManyToOne
    @JoinColumn( name = "acl_object_id" )
    private V object;

    @ElementCollection
    @Enumerated( value = EnumType.STRING )
    @CollectionTable( name = "acl_subject_permissions", joinColumns = @JoinColumn( name = "acl_subject_permission_id") )
    private Set<AclPermission> permissions = new HashSet<AclPermission>();

    @Override
    public void setSubject( T subject ) {
        this.subject = subject;
    }

    @Override
    public T getSubject() {
        return this.subject;
    }

    @Override
    public void setObject( V object ) {
        this.object = object;
    }

    @Override
    public V getObject() {
        return this.object;
    }

    @Override
    public Set<AclPermission> getPermissions() {
        return this.permissions;
    }

    @Override
    public void addPermission( AclPermission permission ) {
        this.permissions.add(permission);
    }

    @Override
    public void removePermission( AclPermission permission ) {
        for ( AclPermission subjectPermission : this.getPermissions() ) {
            if ( subjectPermission.equals(permission) ) {
                this.permissions.remove(subjectPermission);
                break;
            }
        }
    }

}
