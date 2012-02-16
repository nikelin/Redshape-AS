package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.IFormItem;
import com.redshape.form.RenderMode;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.impl
 * @date 8/31/11 1:09 PM
 */
public abstract class AbstractFormItemRenderer<T extends IFormItem> implements IRenderer<T, String> {
    private IRenderersFactory renderersFactory;

    public AbstractFormItemRenderer( IRenderersFactory renderersFactory ) {
        this.renderersFactory = renderersFactory;
    }
    
    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }

    @Override
    public String render(T item ) {
        return this.render( item, RenderMode.FULL );
    }
    
    protected abstract String render( T item, RenderMode renderMode );

    protected void buildAttributes( StringBuilder builder, IFormItem field ) {
		Map<String, Object> attributes = field.getAttributes();
		for ( String key : attributes.keySet() ) {
			Object value = attributes.get(key);
			if ( value == null ) {
				continue;
			}

			builder.append(" ").append( key ).append("=\"").append( attributes.get(key) ).append("\" ");
		}
	}

}
