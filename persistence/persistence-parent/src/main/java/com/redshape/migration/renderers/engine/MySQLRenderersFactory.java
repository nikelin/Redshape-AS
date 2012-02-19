package com.redshape.migration.renderers.engine;

import com.redshape.migration.components.*;
import com.redshape.migration.renderers.mysql.*;
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
