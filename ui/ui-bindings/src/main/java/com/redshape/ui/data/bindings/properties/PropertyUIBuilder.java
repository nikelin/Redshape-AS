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

package com.redshape.ui.data.bindings.properties;

import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.ui.data.bindings.render.IViewRenderer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class PropertyUIBuilder implements IPropertyUIBuilder {
    private static final Logger log = Logger.getLogger( PropertyUIBuilder.class );
    private Map<Class<?>, Class<? extends IPropertyUI<?, ?>>>
            renderers = new HashMap<Class<?>, Class<? extends IPropertyUI<?, ?>>>();
    private Class<? extends IPropertyUI<?, ?>> listRenderer;

    public void setListRenderer( Class<? extends IPropertyUI<?, ?>> renderer ) {
        this.listRenderer = renderer;
    }

    @SuppressWarnings("unchecked")
    public <T, V> IPropertyUI<T, V> createListRenderer( IViewRenderer<?, ?> renderingContext,
                                                        IBindable descriptor ) {
        try {
            return (IPropertyUI<T,V>) this.listRenderer
                    .getConstructor( IViewRenderer.class, IBindable.class )
                    .newInstance( renderingContext,descriptor );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, V> IPropertyUI<T, V> createRenderer(IBindable descriptor) {
        try {
            Class<?> type = descriptor.getType();
            if ( type.isArray() ) {
                type = type.getComponentType();
            }

            for ( Class<?> renderable: this.renderers.keySet() ) {
                if ( !renderable.isAssignableFrom( type ) ) {
                    continue;
                }

                IPropertyUI<T,V > ui = (IPropertyUI<T, V>) this.renderers.get( renderable )
                        .getConstructor( IBindable.class )
                        .newInstance(descriptor);

                return ui;
            }

            throw new IllegalArgumentException( "Unsupported type ( " + descriptor.getType().getCanonicalName() + " ) !" );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            return null;
        }
    }

    @Override
    public <T> void registerRenderer(Class<T> type,
                                     Class<? extends IPropertyUI<T, ?>> renderer) {
        this.renderers.put( type, renderer );
    }

    public void setRenderers( Map<Class<?>, Class<? extends IPropertyUI<?, ?>>> renderers ) {
        this.renderers = renderers;
    }

}
