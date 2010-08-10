package com.vio.persistence.entities.acl;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:37:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclObject {

    /**
     * Get id of current object
     * @return
     */
    public Integer getId();

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
    public void grantAccess( IAclSubject subject, AclPermission permission ) throws AclException;

    /**
     * Revoke specified permission from given subject
     *
     * @param subject
     * @param permission
     * @throws AclException
     */
    public void revokeAccess( IAclSubject subject, AclPermission permission ) throws AclException;

    /**
     * Revoke all permissions granted to specified subject
     *
     * @param subject
     */
    public void denySubject( IAclSubject subject );

}
