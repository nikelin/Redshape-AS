package com.redshape.renderer.forms.decorators;

import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;

public class FormFieldDecorator extends AbstractDecorator {

	@Override
	public String decorate( IFormItem item, String data ) {
		if ( !( item instanceof IFormField ) ) {
			return data;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("<div class=\"field\">")
			   .append("<span class=\"element\">").append( data ).append("</span>")
			   .append("</div>");
		
		return builder.toString();
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
