package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.RenderMode;
import com.redshape.form.decorators.IDecorator;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.forms.decorators.ErrorsDecorator;
import com.redshape.renderer.forms.decorators.FormFieldDecorator;
import com.redshape.renderer.forms.decorators.LabelDecorator;
import com.redshape.renderer.forms.decorators.LegendDecorator;

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


    protected String applyDecorators( IFormItem item, StringBuilder data, RenderMode mode ) {
        this.checkDecorators(item);

        String result = data.toString();
        if ( mode.equals(RenderMode.WITHOUT_DECORATORS) ) {
            return result;
        }

        for ( IDecorator<String> decorator : item.<String>getDecorators() ) {
            result = decorator.decorate(item, result);
        }

        return result;
    }

    protected void checkDecorators( IFormItem field ) {
        if ( field instanceof IFormField && !field.hasDecorator(LabelDecorator.class) ) {
            field.setDecorator( new LabelDecorator() );
        }

        if ( field instanceof IForm && !field.hasDecorator(LegendDecorator.class) ) {
            field.setDecorator( new LegendDecorator() );
        }

        if ( !field.hasDecorator(ErrorsDecorator.class) ) {
            field.setDecorator( new ErrorsDecorator() );
        }

        if ( field instanceof IFormField && !field.hasDecorator( FormFieldDecorator.class ) ) {
            field.setDecorator( new FormFieldDecorator() );
        }
    }

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
