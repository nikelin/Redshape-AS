package com.redshape.renderer.json.renderers;

import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.Commons;
import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {3:44 PM}
 */
public class StandardJSONRenderer extends AbstractJSONRenderer<Object> {

    private IRenderersFactory renderersFactory;
    private Map<Class<?>, Method> handlers = new HashedMap();

    public StandardJSONRenderer( IRenderersFactory renderersFactory ) {
        this.renderersFactory = renderersFactory;

        this.init();
    }

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }

    @Override
    public void repaint(Object renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String render(Object renderable) {
        if ( renderable.getClass().isArray() ) {
            return this.renderArray( (Object[]) renderable);
        }
        
        Method method = null;
        for ( Map.Entry<Class<?>, Method> entry : this.handlers.entrySet() ) {
            if ( entry.getKey().isAssignableFrom(renderable.getClass())
                    && !entry.getKey().isArray() ) {
                method = entry.getValue();
                break;
            }
        }

        if ( method == null ) {
            return "<FILTERED>";
        }
        
        try {
            return String.valueOf(method.invoke(this, renderable));
        } catch ( Throwable e ) {
            return "null";
        }
    }

    public String renderArray( Object[] renderable ) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for ( Object object : renderable ) {
            builder.append( this.render(object) );
        }
        builder.append("]");

        return builder.toString();
    }

    public String render(Map<String, Object> renderable) {
        return this.createObject(renderable);
    }
    
    public String render( Throwable renderable ) {
        if ( renderable == null ) {
            return "<EXCEPTION>";
        }

        return this.createObject(
            this.createField("message", this.render(renderable.getMessage()) ),
            this.createField("type", this.render(renderable.getClass().getName()) )
        );
    }

    public String render(Number renderable) {
        return String.valueOf( renderable );
    }

    public String render(String renderable) {
        return "\"" + Commons.select(renderable, "") + "\"";
    }

    public String render( Collection<?> renderable ) {
        return this.render( renderable.toArray( new Object[renderable.size()] ) );
    }

    public String render( Enum<?> renderable ) {
        return this.render( renderable.name() );
    }

    public String render( Boolean renderable ) {
        return String.valueOf(renderable);
    }
    
    protected boolean isHandlerMethod( Method method ) {
        return method.getName().equals("render")
                && !method.getParameterTypes()[0].equals(Object.class);
    }
    
    protected void init() {
        for ( Method method : this.getClass().getDeclaredMethods() ) {
            if ( !this.isHandlerMethod(method) ) {
                continue;
            }
            
            this.handlers.put( method.getParameterTypes()[0], method );
        }
    }
}
