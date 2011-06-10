package com.redshape.servlet.form.render.impl.fields;

import java.util.Map;

import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.CheckboxField;
import com.redshape.servlet.form.render.IFormFieldRenderer;

public class CheckboxFieldRenderer implements IFormFieldRenderer<CheckboxField> {

	@Override
	public String render(CheckboxField field) {
		StringBuilder builder = new StringBuilder();
		builder.append("<input ")
			   .append(" type=\"checkbox\" ");
		
		builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");
		
		if ( field.getId() != null ) {
			builder.append("id=\"").append( field.getId() ).append("\" ");
		}
		
		if ( field.getValue() != null && field.getValue() ) {
			builder.append("checked=\"checked\" ");
		}
		
		Map<String, Object> attributes = field.getAttributes();
		for ( String key : attributes.keySet() ) {
			builder.append( key ).append("=\"").append( attributes.get(key) ).append("\"");
		}
		
		builder.append("/>");
		
		String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate( field, data );
		}
		
		return data;
	}

}
