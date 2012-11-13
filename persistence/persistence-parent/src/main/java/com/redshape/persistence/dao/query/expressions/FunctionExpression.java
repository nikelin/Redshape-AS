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

package com.redshape.persistence.dao.query.expressions;

import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * @package com.redshape.persistence.dao.query.expressions
 * @user cyril
 * @date 7/18/11 1:22 PM
 */
public class FunctionExpression implements IExpression {
    private String name;
    private IStatement[] terms;

    public FunctionExpression( String name, IStatement... terms ) {
        this.name = name;
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStatement[] getTerms() {
        return terms;
    }

    public void setTerms(IStatement[] terms) {
        this.terms = terms;
    }
}
