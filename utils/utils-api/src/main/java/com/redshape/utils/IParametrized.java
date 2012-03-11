package com.redshape.utils;

/**
 * @author nikelin
 * @date 13:18
 */
public interface IParametrized<T> {

    public <V> V getAttribute( T name );
    
    public void setAttribute( T name, Object value );
    
    public boolean hasAttribute( T name );

}
