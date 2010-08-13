package com.vio.persistence.entities.acl;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:37:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclObject<T extends IAclObject, V extends IAclSubject> {

    /**
     * Get id of current object
     * @return
     */
    public Integer getId();

    /**
     * Set name of current object
     *
     * @param name
     * @return
     */
    public void setName( String name );

    /**
     * Get name of current object
     * @return
     */
    public String getName();

    /**
     * Grant specified permission to given subject
     * 
     * @param subject
     * @param permission
     * @throws AclException
     */
    public void grantAccess( V subject, AclPermission permission ) throws AclException;

    /**
     * Revoke specified permission from given subject
     *
     * @param subject
     * @param permission
     * @throws AclException
     */
    public void revokeAccess( V subject, AclPermission permission ) throws AclException;

    /**
     * Revoke all permissions granted to specified subject
     *
     * @param subject
     */
    public void denySubject( V subject );

    /**
     * Get all permissions granted to given subject
     *
     * @return
     */
    public Set<IAclSubjectPermission<V, T>> getPermissions();

}
