package com.redshape.persistence.entities.acl;

import com.redshape.acl.IAclPermission;
import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 20, 2010
 * Time: 4:44:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity( name = "acl_permissions" )
public class AclPermission extends AbstractEntity<AclPermission> implements IAclPermission {
    @Basic
    private String permission;

    public AclPermission() {
        this(null);
    }

    public AclPermission( String name ) {
        super();
        
        this.permission = name;
    }

    public String getName() {
        return this.permission;
    }
    
}
