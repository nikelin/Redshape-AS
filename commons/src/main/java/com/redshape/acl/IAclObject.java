package com.redshape.acl;

import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:37:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclObject<T extends IAclPermission> {

    /**
         * Get id of current object
         * @return
         */
    public Integer getId();

    /**
         * Get all permissions applicable to current object
         * @return
         */
    public Collection<T> getPermissions();

    /**
         * Check that object applicable to given permission
         * @param permission
         */
    public boolean hasPermission( T permission );

    /**
         *  Add new permission limitation to object
         */
    public void addPermission( T permission );

}
