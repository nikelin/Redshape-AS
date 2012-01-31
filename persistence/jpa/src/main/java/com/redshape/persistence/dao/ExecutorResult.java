package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.executors.IExecutorResult;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExecutorResult<T extends IEntity> implements IExecutorResult<T> {
    private List<? extends Object> results = new ArrayList<Object>();

    public ExecutorResult(Object object) {
        if (object == null) {
            return;
        }

        if (object instanceof Collection) {
            this.results.addAll((Collection) object);
        } else {
            this.results = Commons.list(object);
        }
    }

    public ExecutorResult(List<?> results) {
        this.results = (List<Object>) results;
    }

    @Override
    public <Z> List<Z> getValuesList() {
        return (List<Z>) this.results;
    }

    @Override
    public <Z> Z getSingleValue() {
        return (Z) Commons.firstOrNull(this.results);
    }

    @Override
    public List<T> getResultsList() {
        return (List<T>) this.results;
    }

    @Override
    public T getSingleResult() {
        return (T) Commons.firstOrNull(this.results);
    }

    @Override
    public int count() {
        return this.results.size();
    }
}