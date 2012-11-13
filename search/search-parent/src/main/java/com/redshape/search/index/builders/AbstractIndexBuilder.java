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

package com.redshape.search.index.builders;

import com.redshape.search.index.visitor.field.IFieldVisitor;
import com.redshape.search.index.visitor.field.StandardFieldVisitor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 3:18:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIndexBuilder implements IIndexBuilder {
    private IFieldVisitor fieldVisitor;

    @Override
    public IFieldVisitor getFieldVisitor() {
        if ( this.fieldVisitor == null ) {
            this.fieldVisitor = new StandardFieldVisitor(this);
        }

        return this.fieldVisitor;
    }

    @Override
    public void setFieldVisitor( IFieldVisitor visitor ) {
        this.fieldVisitor = visitor;
    }

}
