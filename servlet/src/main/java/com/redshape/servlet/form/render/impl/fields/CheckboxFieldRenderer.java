package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.CheckboxField;

public class CheckboxFieldRenderer extends AbstractFormFieldRenderer<CheckboxField> {

	@Override
	public String render(CheckboxField field, RenderMode mode) {
		StringBuilder builder = new StringBuilder();
		builder.append("<input ")
			   .append(" type=\"checkbox\" ");

		builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");
		
		if ( field.getId() != null ) {
			builder.append("id=\"").append( field.getId() ).append("\" ");
		}
		
		if ( field.getValue() != null ) {
            builder.append("checked=\"checked\"");
		}

		this.buildAttributes( builder, field );
		
		builder.append("/>");
		
		String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate( field, data );
		}
		
		return data;
	}

}
