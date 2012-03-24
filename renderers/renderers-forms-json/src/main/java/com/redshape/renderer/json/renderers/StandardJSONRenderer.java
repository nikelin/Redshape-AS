package com.redshape.renderer.json.renderers;

import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.Commons;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {3:44 PM}
 */
public class StandardJSONRenderer extends AbstractJSONRenderer<Object> {

    private IRenderersFactory renderersFactory;
    private Map<Class<?>, Method> handlers = new HashMap();

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
            IRenderer<Object, String> renderer =  this.getRenderersFactory()
                        .<Object, String>forEntity(renderable);
            if ( renderer != null && renderer != this ) {
                return renderer.render(renderable);
            }

            return this.render("<FILTERED>");
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
        int i = 0;
        for ( Object object : renderable ) {
            builder.append( this.render(object) );
            if ( i++ != renderable.length - 1 ) {
                builder.append(",");
            }
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
