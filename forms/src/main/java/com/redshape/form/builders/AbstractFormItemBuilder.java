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

package com.redshape.form.builders;

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.renderer.IRenderersFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form.builders
 * @date 8/26/11 4:00 PM
 */
public abstract class AbstractFormItemBuilder implements IFormItemBuilder {
    protected String id;
    protected String name;
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    protected Collection<IDecorator> decorators = new ArrayList<IDecorator>();
    protected Map<DecoratorAttribute, Object> decoratorAttributes
            = new HashMap<DecoratorAttribute, Object>();

    private IRenderersFactory renderersFactory;

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }

    @Override
    public IFormItemBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    @Override
    public IFormFieldBuilder asFieldBuilder() {
        return (IFormFieldBuilder) this;
    }

    @Override
    public IFormBuilder asFormBuilder() {
        return (IFormBuilder) this;
    }

    @Override
    public IFormItemBuilder withId( String id ) {
        this.id = id;
        return this;
    }

    @Override
    public IFormItemBuilder withDecoratorAttribute(DecoratorAttribute name, Object value) {
        this.decoratorAttributes.put(name, value);
        return this;
    }

    @Override
    public IFormItemBuilder withDecoratorAttributes(Map<DecoratorAttribute, Object> attributes) {
        this.decoratorAttributes.putAll( attributes );
        return this;
    }

    @Override
    public IFormItemBuilder withEmptyDecorators() {
        this.decorators.clear();
        return this;
    }

    @Override
    public IFormItemBuilder withDecorator(IDecorator decorator) {
        this.decorators.add( decorator );
        return this;
    }

    @Override
    public IFormItemBuilder withDecorators(Collection<IDecorator> decorators) {
        this.decorators.addAll(decorators);
        return this;
    }

    @Override
    public IFormItemBuilder withAttribute(String name, Object value) {
        this.attributes.put(name, value);
        return this;
    }
}
