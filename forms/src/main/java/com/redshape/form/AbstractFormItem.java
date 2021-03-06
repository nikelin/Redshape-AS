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

package com.redshape.form;

import com.redshape.form.decorators.IDecorator;
import com.redshape.renderer.managers.RenderersManager;
import com.redshape.utils.Commons;
import com.redshape.utils.SimpleStringUtils;

import java.util.*;

public abstract class AbstractFormItem implements IFormItem {
    public static String PATH_SEPARATOR = ".";

    private static final long serialVersionUID = 4768154716952314774L;

    private List<IDecorator<?>> decorators = new ArrayList<IDecorator<?>>();
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private String id;
    private String name;
    private boolean dirtyState;
    private List<String> messages = new ArrayList<String>();
    private IForm context;

    public AbstractFormItem( String id ) {
        this(id, id);
    }

    public AbstractFormItem( String id, String name ) {
        super();

        this.id = id;
        this.name = name;
    }

    public void clearMessages() {
        this.messages.clear();
        this.makeDirty();
    }

    public void addMessage( String message ) {
        this.messages.add(message);
        this.makeDirty();
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void setContext(IForm form) {
        this.context = form;
    }

    @Override
    public IForm getContext() {
        return this.context;
    }

    @Override
    public String getCanonicalName() {
        StringBuilder builder = new StringBuilder();
        IForm context = this.getContext();
        while ( context != null ) {
            String name = Commons.select( context.getName(), context.getId() );
            if ( name != null && !name.equals("null") ) {
                builder.append( name )
                        .append( PATH_SEPARATOR );
            }

            context = context.getContext();
        }

        String result = SimpleStringUtils.reverseSentence(builder.toString(), PATH_SEPARATOR);
        builder.delete(0, builder.length() );

        if ( !result.isEmpty() ) {
            builder.append( result )
                    .append( PATH_SEPARATOR );
        }


        builder.append( Commons.select( this.getName(), this.getId() ) );

        return builder.toString();
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAttribute(String name) {
        return (T) this.attributes.get(name);
    }

    @Override
    public void setDecorator(IDecorator decorator) {
        if ( !this.decorators.contains(decorator) ) {
            this.decorators.add(decorator);
            this.makeDirty();
        }
    }

    @Override
    public void setDecorators(IDecorator[] decorators) {
        for ( IDecorator decorator : decorators ) {
            this.setDecorator( decorator );
        }
    }

    @Override
    public boolean hasDecorator( Class<? extends IDecorator<?>> decorator ) {
        for ( IDecorator<?> registeredDecorator : this.decorators ) {
            if ( decorator.equals( registeredDecorator.getClass() ) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setRawElement( Object object ) {
        this.setAttribute("domElement", object);
    }

    @Override
    public <V> V getRawElement() {
        return this.getAttribute("domElement");
    }

    @Override
    public void clearDecorators() {
        this.decorators.clear();
    }

    @Override
    public void setDecorators(List<IDecorator<?>> decorators) {
        this.decorators = decorators;
    }

    @Override
    public <T> Collection<IDecorator<T>> getDecorators() {
        return (Collection) this.decorators;
    }

    @Override
    public boolean isDirty() {
        return this.dirtyState;
    }

    @Override
    public void makeDirty() {
        this.dirtyState = true;
        if ( this.getContext() != null ) {
            this.getContext().makeDirty();;
        }

        RenderersManager.getInstance().addInvalid(this);
    }

    @Override
    public void makeClean() {
        this.dirtyState = false;
        RenderersManager.getInstance().markValid(this);
    }
}