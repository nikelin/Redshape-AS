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

public class EqualsOperation implements IExpression {
    protected IStatement left;
    protected IStatement right;

    public EqualsOperation(IStatement left, IStatement right) {
        this.left = left;
        this.right = right;
    }

    public IStatement getLeftOperand() {
        return this.left;
    }

    public IStatement getRightOperand() {
        return this.right;
    }
}
