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

package com.redshape.search.query.terms.impl;

import com.redshape.search.query.terms.IBinaryTerm;
import com.redshape.search.query.terms.ISearchTerm;
import com.redshape.search.query.terms.Operation;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 11:57:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrTerm implements IBinaryTerm {
    private ISearchTerm first;
    private ISearchTerm second;

    public OrTerm() {
        this(null, null);
    }

    public OrTerm( ISearchTerm first, ISearchTerm second ) {
        this.first = first;
        this.second = second;
    }

    public ISearchTerm getLeft() {
        return this.first;
    }

    public ISearchTerm getRight() {
        return this.second;
    }

    public void setLeft(ISearchTerm first) {
        this.first = first;
    }

    public void setRight(ISearchTerm second) {
        this.second = second;
    }

    public Operation getOperation() {
        return Operation.OR;
    }

}
