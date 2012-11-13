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
