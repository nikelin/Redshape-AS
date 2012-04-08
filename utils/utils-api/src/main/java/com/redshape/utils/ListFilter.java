package com.redshape.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 4/8/12
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListFilter<T> implements IFiltersList<T> {

    private List<IFilter<T>> filters = new ArrayList<IFilter<T>>();

    public ListFilter(List<IFilter<T>> filters) {
        Commons.checkNotNull(filters);
        
        this.filters = filters;
    }

    @Override
    public void addFilter(IFilter<T> filter) {
        Commons.checkNotNull(filter);
        
        this.filters.add(filter);
    }

    @Override
    public void removeFilter(IFilter<T> filter) {
        Commons.checkNotNull(filter);

        this.filters.remove(filter);
    }

    @Override
    public boolean filter(T filterable) {
        for ( IFilter<T> filter : this.filters ) {
            if ( !filter.filter(filterable) ) {
                return false;
            }
        }

        return true;
    }
}
