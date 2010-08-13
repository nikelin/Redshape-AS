package com.vio.auth.adapters;

import com.vio.auth.AuthResult;
import com.vio.auth.IIdentity;
import com.vio.auth.storage.AuthStorage;

import java.util.Map;

/**
 * Интерфейс для аутентификаторов системы
 */
public interface AuthenticatorInterface<T extends IIdentity> {

    public boolean isAuthExpired( T identity );
    /**
     * Аутентифицировать переданную сущность
     *
     * @param identity
     * @return AuthResult
     */
    public AuthResult<T> authenticate( T identity );

    /**
     * Установить хранилище для данного аутентификатора
     * @param store
     * @return AuthentificatorInterface
     */
    public void setStorage( AuthStorage<T> store );

    /**
     * Проверить существование соответствия между переданным ID и сущность в хранилище
     *
     * @param id
     * @return Identity
     */
    public T getIdentity( Object id );

    /**
     * Получить все авторизованные сущности
     *
     * @return List<Identity>
     */
    public Map<Object, T> getAuthenticated();

    /**
     * Удалить переданную сущность из списка авторизованных
     *
     * @param identity
     * @return AuthenticatorInterface
     */
    public void unauthorize( T identity ); 

}
