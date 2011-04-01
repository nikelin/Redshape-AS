package com.redshape.auth;

/**
 * Объект результата работы аутентификатора
 *
 * @author nikelin
 */
public class AuthResult {
    /**
     * Перечисление возможным статусов аутентификации
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
     * @param status Результат аутентификации
     * @param identity Аутентифицируемая сущность
     */
    public AuthResult(Status status, IIdentity identity ) {
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
