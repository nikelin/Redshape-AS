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

package com.redshape.renderer.forms.renderers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.IDecorator;
import com.redshape.renderer.IRenderer;
import com.redshape.utils.Commons;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/17/12 {3:47 PM}
 */
public abstract class AbstractGWTRenderer<T extends IFormItem> implements IRenderer<T, Widget> {

    private boolean doResultsCache;
    private Map<Class<?>, Widget> cache = new HashMap<Class<?>, Widget>();
    
    protected boolean isDoResultsCache() {
        return this.doResultsCache;
    }
    
    public void doResultsCache( boolean value ) {
        this.doResultsCache = value;
    }

    protected Widget restoreCache( Class<?> renderable ) {
        return this.cache.get(renderable);
    }
    
    protected void saveCache( Class<?> renderable, Widget result ) {
        this.cache.put( renderable, result );
    }

    protected Widget applyDecorators( T item, Widget widget ) {
        Commons.checkNotNull(item);
        Commons.checkNotNull(widget);

        Widget result = widget;
        for ( IDecorator<Widget> decorator : item.<Widget>getDecorators() ) {
            if ( decorator != null ) {
                result = decorator.decorate( item, result );
            }
        }

        return result;
    }

    @Override
    public void repaint(T renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void buildAttributes( Widget widget, T item ) {
        this.buildAttributes(widget.getElement(), item);
    }

    protected void buildAttributes( Element element, T item ) {
        for (Map.Entry<String, Object> entry : item.getAttributes().entrySet() ) {
            element.setAttribute(
                entry.getKey(),
                entry.getValue() != null ?
                    String.valueOf(entry.getValue()) :
                    "" );
        }
    }
    
}
