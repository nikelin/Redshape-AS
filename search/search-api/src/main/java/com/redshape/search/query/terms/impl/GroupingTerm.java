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

import com.redshape.search.query.terms.IGroupingTerm;
import com.redshape.search.query.terms.ISearchTerm;
import com.redshape.search.query.terms.Operation;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public class GroupingTerm implements IGroupingTerm {
	private ISearchTerm[] terms;
	private Operation operation;

    public GroupingTerm() {
        this(null, null);
    }

    public GroupingTerm( ISearchTerm[] terms, Operation operation ) {
		this.terms = terms;
		this.operation = operation;
	}

	@Override
	public Operation getOperation() {
		return this.operation;
	}

	@Override
	public ISearchTerm[] getList() {
		return this.terms;
	}

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setList(ISearchTerm[] array) {
        this.terms = array;
    }

}
