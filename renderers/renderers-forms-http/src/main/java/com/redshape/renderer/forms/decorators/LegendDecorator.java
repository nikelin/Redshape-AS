package com.redshape.renderer.forms.decorators;

import com.redshape.form.IForm;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.i18n.impl.StandardI18NFacade;

public class LegendDecorator extends AbstractDecorator {

	@Override
	public String decorate(IFormItem item, String data) {
		if ( ! ( item instanceof IForm ) ) {
			return data;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<fieldset ");

		if ( item instanceof IForm && ( (IForm) item ).getContext() != null ) {
			this.buildAttributes( item.getAttributes(), builder );
		}

	   	builder.append(">");
		builder.append("<legend>")
		       .append( StandardI18NFacade._( ( (IForm) item).getLegend() ) )
		       .append("</legend>");
		
		builder.append(data);
		
		builder.append("</fieldset>");
		
		return builder.toString();
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
