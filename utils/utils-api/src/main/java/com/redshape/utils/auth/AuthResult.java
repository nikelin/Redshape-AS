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

package com.redshape.utils.auth;

/**
 * Object to represents result of authentication
 *
 * @author nikelin
 */
public class AuthResult {
    /**
     * Authentication statuses
     */
    public enum Status {
       SUCCESS,
       INVALID_CREDENTIALS,
       INVALID_IDENTITY,
       INACTIVE_IDENTITY,
       EXPIRED_IDENTITY,
       FAIL,
       NON_CONFIRMED,
    }

    private Status status = Status.FAIL;
    private IIdentity identity;

    public AuthResult( Status status ) {
        this(status, null);
    }

    /**
     * @param status Authentication result
     * @param identity
     */
    public AuthResult( Status status, IIdentity identity ) {
       this.identity = identity;
       this.status = status;
    }

    @SuppressWarnings("unchecked")
	public <T extends IIdentity> T getIdentity() {
       return (T) this.identity;
    }

    public Status getStatus() {
       return this.status;    
    }
}
