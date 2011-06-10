package com.redshape.servlet.form.decorators;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;

public class FormFieldDecorator extends AbstractDecorator {

	@Override
	public String decorate( IFormItem item, String data ) {
		if ( !( item instanceof IFormField ) ) {
			return data;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("<dl>");
		
		String label = ( (IFormField<?>) item ).getLabel();
		if ( label != null ) {
			builder.append("<dt>").append( StandardI18NFacade._( label ) ).append("</dt>");
		}
		
		builder.append("<dd>").append( data ).append("</dd>");
		builder.append("</dl>");
		
		return builder.toString();
	}
	
}
