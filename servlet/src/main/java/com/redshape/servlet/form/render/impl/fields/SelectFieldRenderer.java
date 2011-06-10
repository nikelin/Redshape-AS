package com.redshape.servlet.form.render.impl.fields;

import java.util.Map;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.SelectField;
import com.redshape.servlet.form.render.IFormFieldRenderer;

public class SelectFieldRenderer implements IFormFieldRenderer<SelectField<?>> {

	@Override
	public String render(SelectField<?> field) {
		StringBuilder builder = new StringBuilder();
		builder.append("<select ");
		
		builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");

		
		if ( field.getId() != null ) {
			builder.append("id=\"").append( field.getId() ).append("\" ");
		}
		
		Map<String, Object> attributes = field.getAttributes();
		for ( String key : attributes.keySet() ) {
			builder.append( key ).append("=\"").append( attributes.get(key) ).append("\" ");
		}
		
		builder.append(">");
		
		Map<String, ?> options = field.getOptions();
		for ( String key : options.keySet() ) {
			builder.append("<option name=\"").append(key).append("\"> ")
				   .append( StandardI18NFacade._( String.valueOf( options.get(key) ) ) )
				   .append("</option> ");
		}
		
		builder.append("</select>");
		
		String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}
		
		return data;
	}

}
