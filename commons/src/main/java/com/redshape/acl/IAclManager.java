/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.acl;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 20, 2010
 * Time: 4:21:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAclManager<T extends IAclSubjectPermission<?,?,?>> {

    public void grantAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;

    public void revokeAccess( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;

    public  void denySubject( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException;

    public Collection<T> getSubjectPermissions( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object ) throws AclException;

    public T getSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;
    
    /**
         * @param permission
         * @return
         */
    public IAclPermission createPermission( String permission );

    public T createSubjectPermission( IAclSubject<T> subject, IAclObject<? extends IAclPermission> object, IAclPermission permission ) throws AclException;
}
