package com.redshape.auth;

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
