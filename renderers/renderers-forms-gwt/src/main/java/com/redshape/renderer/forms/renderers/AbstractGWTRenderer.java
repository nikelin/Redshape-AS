package com.redshape.renderer.forms.renderers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.IDecorator;
import com.redshape.renderer.IRenderer;
import com.redshape.utils.Commons;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/17/12 {3:47 PM}
 */
public abstract class AbstractGWTRenderer<T extends IFormItem> implements IRenderer<T, Widget> {
    
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
    
    protected void buildAttributes( Widget widget, T item ) {
        Element element = widget.getElement();
        for (Map.Entry<String, Object> entry : item.getAttributes().entrySet() ) {
            element.setAttribute(
                entry.getKey(),
                entry.getValue() != null ?
                    String.valueOf(entry.getValue()) :
                    "" );
        }
    }
    
}
