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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface for forms and their content.
 *
 * Needs to implement most common form item. 
 *
 * @author nikelin
 */
public interface IFormItem extends Serializable {
    
    /**
     * Bind within given raw item (only for CLIENT SIDE code)
     */
    public void setRawElement( Object element );

    /**
     * Return raw item representation (only for CLIENT SIDE code)
     * @param <V>
     * @return
     */
    public <V> V getRawElement();

    /**
     * Set name to field
     * @param name
     */
    public void setName( String name );

    /**
     * Returns field name
     * @return
     */
    public String getName();

    /**
     * Returns current form item identifier
     * @return
     */
    public String getId();

    /**
     * ID associated with current form item.
     * After rendering it's parameter will be the value for XML `id` attribute
     * node.
     *
     * @return
     */
    public void setId( String id );

    /**
     * Check that specified attribute presents in
     * the related collection
     * @return
     */
    public boolean hasAttribute( String name );

    /**
     * Return field attributes map.
     *
     * @return
     */
    public Map<String, Object> getAttributes();

    /**
     * Adopt new attribute to the related attributes collection.
     * All specified attributes will be rendered as XML-attribute for current item.
     *
     * @param name
     * @param value
     */
    public void setAttribute( String name, Object value );

    /**
     * Find item with specified name in related collection
     * @param <T>
     * @param name
     * @return
     */
    public <T> T getAttribute( String name );

    /**
     * Adopt given objects to the related decorators collection
     * @param decorators
     */
    public void setDecorators( IDecorator[] decorators );

    /**
     * Adopt new decorator to the related decorators collection
     * @param decorator
     */
    public void setDecorator( IDecorator decorator );

    /**
     * Check is that given decorator type was applied to the current
     * item
     *
     * @return
     */
    public boolean hasDecorator( Class<? extends IDecorator<?>> decorator );

    /**
     * Adopt given objects to the related decorators collection
     * @param decorators
     */
    public void setDecorators(List<IDecorator<?>> decorators);

    /**
     * Return related decorators collection.
     * @return
     */
    public <T> Collection<IDecorator<T>> getDecorators();

    /**
     * Unbind all decorators associated with current field.
     */
    public void clearDecorators();

    /**
     * Proceed validation
     * @return
     */
    public boolean isValid();

    /**
     * Return current item context (sub form / root )
     *
     * @return
     */
    public IForm getContext();

    /**
     * Change current item context (after adopting as another context structure part)
     *
     * @param form
     */
    public void setContext( IForm form );

    /**
     * Reset validation result for current field and its children
     * elements
     */
    public void resetMessages();

    /**
     * Reset state
     */
    public void resetState();

    /**
     * Return full name for the current field.
     *
     * Full name includes not only field self name but also
     * path from root form which contains all sub form names as
     * a nodes separated by "." (dot)
     *
     * @return
     */
    public String getCanonicalName();

    /**
     * Add error message to the given field
     *
     * @param message
     */
    public void addMessage( String message );

    /**
     * Messages
     * @return
     */
    public List<String> getMessages();

    public boolean isDirty();

    public void makeDirty();

    public void makeClean();

}
