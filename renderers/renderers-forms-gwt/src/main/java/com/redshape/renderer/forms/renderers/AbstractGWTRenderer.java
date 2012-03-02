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
