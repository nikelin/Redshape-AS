package com.redshape.persistence.dao.query.statements;

public class ScalarStatement<T> implements IStatement {
    private T value;

    public ScalarStatement(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

}

