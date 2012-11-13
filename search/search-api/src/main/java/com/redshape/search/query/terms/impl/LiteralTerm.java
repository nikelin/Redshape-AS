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

import com.redshape.search.query.terms.IScalarTerm;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 1:54:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiteralTerm implements IScalarTerm {
    private Object value;

    public LiteralTerm() {
        this(null);
    }

    public LiteralTerm( Object value ) {
        this.value = value;
    }

	@Override
	public <T> T getValue() {
		return (T) this.value;
	}

    public <T> void setValue(T value) {
        this.value = value;
    }

}
