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

package com.redshape.persistence.migration.renderers.engine;

import com.redshape.persistence.migration.components.*;
import com.redshape.persistence.migration.renderers.mysql.*;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;

import java.lang.reflect.Constructor;


/**
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.engine
 * @date Apr 6, 2010
 */
public class MySQLRenderersFactory extends AbstractRenderersFactory {

    public MySQLRenderersFactory() {
        super();

        this.init();
    }

    public String getFactoryId() {
        return "mysql";
    }

    @Override
    public <T, V> IRenderer<T,V> forEntity( Class<T> clazz ) {
        Class<? extends IRenderer<T, V>> rendererClazz = null;
        for ( Class<?> entityClazz : this.entities.keySet() ) {
            if ( entityClazz.isAssignableFrom(clazz) ) {
                rendererClazz = (Class<? extends IRenderer<T, V>>) this.entities.get(entityClazz);
                break;
            }
        }

        if ( rendererClazz == null ) {
            return null;
        }

        IRenderer<T, V> renderer = (IRenderer<T, V>) this.renderers.get(rendererClazz);
        if ( renderer != null ) {
            return renderer;
        }

        try {
            Constructor<? extends IRenderer<T, V>> bindConstructor = null;
            for ( Constructor<?> constructor : rendererClazz.getConstructors() ) {
                if ( constructor.getParameterTypes().length == 1
                        && constructor.getParameterTypes()[0].isAssignableFrom(IRenderersFactory.class) ) {
                    bindConstructor = (Constructor<? extends IRenderer<T, V>>) constructor;
                    break;
                }
            }

            if ( bindConstructor != null ) {
                renderer = bindConstructor.newInstance(this);
            } else {
                renderer = rendererClazz.newInstance();
            }
        } catch ( Throwable e ) {
            throw new InstantiationError(e.getMessage());
        }

        this.renderers.put( rendererClazz, renderer );

        return renderer;
    }

    protected void init() {
        this.addRenderer(Field.class, FieldRenderer.class );
        this.addRenderer(DroppingTable.class, DropTableRenderer.class );
        this.addRenderer(Table.class, CreateTableRenderer.class);
        this.addRenderer(FieldType.class, FieldTypeRenderer.class);
        this.addRenderer(TableOption.class, TableOptionRenderer.class );
    }


}
