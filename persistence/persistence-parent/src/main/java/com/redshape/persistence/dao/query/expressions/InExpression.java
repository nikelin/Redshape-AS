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

import com.redshape.persistence.dao.query.statements.IArrayStatement;
import com.redshape.persistence.dao.query.statements.IStatement;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class InExpression implements IExpression {

    private IArrayStatement range;
    private IStatement field;
    
    public InExpression( IStatement field, IArrayStatement range ) {
        this.field = field;
        this.range = range;
    }

    public IArrayStatement getRange() {
        return range;
    }

    public IStatement getField() {
        return field;
    }
}
