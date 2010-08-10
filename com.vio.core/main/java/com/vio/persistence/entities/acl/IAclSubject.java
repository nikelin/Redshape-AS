package com.vio.persistence.entities.acl;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:36:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclSubject {

    /**
     * Check ability of current subject process any actions under given permission
     * 
     * @param object
     * @param permission
     * @return
     * @throws AclException
     */
    public boolean canAccess( IAclObject object, AclPermission permission ) throws AclException;

    public Collection<AclPermission> getAllPermissions( IAclObject object ) throws AclException;
    
}
