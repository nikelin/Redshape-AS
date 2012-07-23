package com.redshape.renderer.json;

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.json.renderers.StandardFormFieldRenderer;
import com.redshape.renderer.json.renderers.StandardFormsRenderers;
import com.redshape.renderer.json.renderers.StandardJSONRenderer;
import com.redshape.renderer.json.renderers.StandardViewRenderer;
import com.redshape.servlet.views.IView;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json
 * @date 3/1/12 {2:52 PM}
 */
public class JSONRenderersFactory extends AbstractRenderersFactory {

    private boolean reflectiveRenderEnabled;

    public JSONRenderersFactory() {
        this( new HashMap<Class<?>, IRenderer<?, ?>>() );
    }

    public JSONRenderersFactory( Map customRenderers) {
        super();

        this.init();
        this.addRenderers((Map) customRenderers);
    }

    public boolean isReflectiveRenderEnabled() {
        return reflectiveRenderEnabled;
    }

    public void setReflectiveRenderEnabled(boolean reflectiveRenderEnabled) {
        this.reflectiveRenderEnabled = reflectiveRenderEnabled;
    }

    @Override
    public String getFactoryId() {
        return "JSON";
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
            rendererClazz = (Class<? extends IRenderer<T, V>>)
                    StandardJSONRenderer.class.asSubclass(IRenderer.class);
        }

        IRenderer<T, V> renderer = (IRenderer<T, V>) this.renderers.get(rendererClazz);
        if ( renderer instanceof StandardJSONRenderer ) {
            if ( this.isReflectiveRenderEnabled() ) {
                ((StandardJSONRenderer) renderer).setReflectiveEnabled(this.isReflectiveRenderEnabled());
            }
        }

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
        this.addRenderer(IFormField.class, StandardFormFieldRenderer.class );
        this.addRenderer(IForm.class, StandardFormsRenderers.class );
        this.addRenderer(IView.class, StandardViewRenderer.class);

        this.addRenderer(Throwable.class,
                (Class<? extends IRenderer<Throwable, String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class) );
        this.addRenderer(Map.class,
                (Class<? extends IRenderer<Map, String>>)
                    StandardJSONRenderer.class.asSubclass(IRenderer.class) );
        this.addRenderer(String.class,
                (Class<? extends IRenderer<String, String>>)
                    StandardJSONRenderer.class.asSubclass(IRenderer.class));
        this.addRenderer(Collection.class,
                (Class<? extends IRenderer<Collection, String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class));
        this.addRenderer(Number.class,
                (Class<? extends IRenderer<Number, String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class));
        this.addRenderer(Enum.class,
                (Class<? extends IRenderer<Enum, String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class) );
        this.addRenderer(Object[].class,
                (Class<? extends IRenderer<Object[], String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class) );
        this.addRenderer(Boolean.class,
                (Class<? extends IRenderer<Boolean, String>>)
                        StandardJSONRenderer.class.asSubclass(IRenderer.class));
    }
}
