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
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinStatement implements IJoinStatement {
    private String name;
    private JoinType joinType;
    private JoinEntityType entityType;
    private IStatement reference;
    private String alias;

    public JoinStatement( JoinEntityType entityType, JoinType joinType, String name, String alias ) {
        this.alias = alias;
        this.name = name;
        this.joinType = joinType;
        this.entityType = entityType;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public JoinType getJoinType() {
        return this.joinType;
    }

    @Override
    public JoinEntityType getJoinEntityType() {
        return this.entityType;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
