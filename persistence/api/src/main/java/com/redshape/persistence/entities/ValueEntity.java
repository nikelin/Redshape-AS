package com.redshape.persistence.entities;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 5/14/12
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueEntity<T> implements IEntity {

    private T value;

    public ValueEntity( T value ) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean isDto() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
