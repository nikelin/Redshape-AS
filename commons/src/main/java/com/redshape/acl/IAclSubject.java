package com.redshape.acl;

/**
 * @param <T extends IAclSubjectPermission>
 */
public interface IAclSubject<T extends IAclSubjectPermission> {

    /**
         * Get integer identifier of ACL object
         * @param id
         * @return
         */
    public Integer getId();

    /**
         * @return Class<T>
         */
    public Class<T> getAclPermissionClass();
    
}
