package com.redshape.auth;

/**
 * Объект результата работы аутентификатора
 *
 * @author nikelin
 */
public class AuthResult<T extends IIdentity> {
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
    private T identity;

    public AuthResult( Status status ) {
        this(status, null);
    }

    /**
     * @param status Результат аутентификации
     * @param identity Аутентифицируемая сущность
     */
    public AuthResult(Status status,  T identity ) {
       this.identity = identity;
       this.status = status;
    }

    public T getIdentity() {
       return this.identity;
    }

    public Status getStatus() {
       return this.status;    
    }
}
