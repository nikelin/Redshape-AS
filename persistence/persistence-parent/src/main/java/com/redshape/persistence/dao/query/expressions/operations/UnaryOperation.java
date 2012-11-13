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

package com.redshape.persistence.dao.query.expressions.operations;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * @package com.redshape.persistence.dao.query.expressions.operations
 * @user cyril
 * @date 7/19/11 5:08 PM
 */
public class UnaryOperation implements IExpression {
    public enum Types {
        NEGATE
    }

    private Types type;
    private IStatement term;

    public UnaryOperation( Types type, IStatement statement ) {
        this.term = statement;
    }

    public Types getType() {
        return type;
    }

    public IStatement getTerm() {
        return this.term;
    }

}
