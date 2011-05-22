package com.redshape.acl;

/**
 * @param <T extends IAclSubjectPermission>
 */
public interface IAclSubject<T extends IAclSubjectPermission<?,?,?>> {


    /**
         * @return Class<T>
         */
    public Class<T> getAclPermissionClass();
    
}
