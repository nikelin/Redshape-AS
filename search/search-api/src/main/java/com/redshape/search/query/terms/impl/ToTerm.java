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
import com.redshape.search.query.terms.RangeType;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:53 PM
 */
public class ToTerm implements IBinaryTerm {
	private ISearchTerm from;
	private ISearchTerm to;
	private RangeType rangeType;

    public ToTerm() {
        this(null, null, null);
    }

    public ToTerm( ISearchTerm from, ISearchTerm to, RangeType constraint ) {
		this.from = from;
		this.to = to;
		this.rangeType = constraint;
	}

	@Override
	public ISearchTerm getLeft() {
		return this.from;
	}

	@Override
	public ISearchTerm getRight() {
		return this.to;
	}

	@Override
	public Operation getOperation() {
		return Operation.TO;
	}

	public RangeType getType() {
		return this.rangeType;
	}

    public void setLeft(ISearchTerm left) {
        this.from = left;
    }


    public void setRight(ISearchTerm right) {
        this.to = right;
    }

    public void setType(RangeType type) {
        this.rangeType = type;
    }
}
