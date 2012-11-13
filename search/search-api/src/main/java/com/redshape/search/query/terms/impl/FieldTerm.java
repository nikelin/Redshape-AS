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

import com.redshape.search.query.terms.IFieldTerm;
import com.redshape.search.query.terms.ISearchTerm;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 2:14 PM
 */
public class FieldTerm implements IFieldTerm {
	private String fieldName;
	private ISearchTerm term;

    public FieldTerm() {
        this(null, null);
    }

    public FieldTerm( String fieldName, ISearchTerm term ) {
		this.fieldName = fieldName;
		this.term = term;
	}

	@Override
	public String getField() {
		return this.fieldName;
	}

	@Override
	public ISearchTerm getTerm() {
		return this.term;
	}

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setTerm(ISearchTerm term) {
        this.term = term;
    }
}
