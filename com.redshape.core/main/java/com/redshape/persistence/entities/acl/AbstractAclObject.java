package com.redshape.persistence.entities.acl;

import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.persistence.entities.IEntity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
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
public abstract class AbstractAclObject<T extends IEntity & IAclObject, V extends IAclSubject> extends AbstractEntity<T> implements IAclObject<T, V> {
    
    @Basic
    private String name;

    @OneToMany( mappedBy = "object", cascade = {CascadeType.ALL} )
    private Set<IAclSubjectPermission<V, T>> permissions = new HashSet();

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    synchronized public void grantAccess( V subject, AclPermission permission ) throws AclException {
        IAclSubjectPermission subjectPermission = this.getSubjectPermission( subject );
        if ( subjectPermission == null ) {
            subjectPermission = this.createSubjectPermission( subject );
            this.permissions.add(subjectPermission);
        }

        subjectPermission.addPermission(permission);
    }

    synchronized public void revokeAccess( V subject, AclPermission permission ) throws AclException {
        for ( IAclSubjectPermission subjectPermission : this.getPermissions() ) {
            if ( subjectPermission.getSubject().equals( subject) ) {
                subjectPermission.removePermission(permission);
            }
        }
    }

    synchronized public void denySubject( V subject ) {

    }

    public Set<IAclSubjectPermission<V, T>> getPermissions() {
        return this.permissions;
    }

    protected IAclSubjectPermission<V, T> getSubjectPermission( V subject ) {
        for ( IAclSubjectPermission subjectPermission : this.getPermissions() ) {
            if ( subjectPermission.getSubject().equals( subject ) ) {
                return subjectPermission;
            }
        }

        return null;
    }

    protected IAclSubjectPermission<V, T> createSubjectPermission( V subject ) {
        AclSubjectPermission subjectPermission = new AclSubjectPermission();
        subjectPermission.setSubject(subject);
        subjectPermission.setObject(this);

        return subjectPermission;
    }

}
