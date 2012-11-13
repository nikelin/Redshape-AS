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

package com.redshape.persistence.dao.query.statements;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class AliasStatement implements IAliasStatement {
    private IStatement source;
    private String target;
    private boolean evaluateSource;

    public AliasStatement(IStatement source, String target) {
        this(source, target, false);
    }

    public AliasStatement(IStatement source, String target, boolean evaluateSource ) {
        this.source = source;
        this.evaluateSource = evaluateSource;
        this.target = target;
    }

    @Override
    public boolean doEvaluateSource() {
        return this.evaluateSource;
    }

    @Override
    public IStatement getSource() {
        return this.source;
    }

    @Override
    public String getTarget() {
        return this.target;
    }
}
