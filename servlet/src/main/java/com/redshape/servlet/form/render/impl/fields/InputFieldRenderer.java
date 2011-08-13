package com.redshape.servlet.form.render.impl.fields;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.InputField;
import com.redshape.servlet.form.render.IFormFieldRenderer;

import java.util.Map;

public class InputFieldRenderer implements IFormFieldRenderer<InputField> {

	@Override
	public String render(InputField field, RenderMode mode ) {
		StringBuilder builder = new StringBuilder();
		builder.append("<input ")
			   .append("type=\"").append( field.getType().name().toLowerCase() ).append("\" ");
		
		if ( field.getId() != null ) {
			builder.append("id=\"").append( field.getId() ).append( "\" ");
		}

        if ( field.getName() != null ) {
		    builder.append("name=\"").append( field.getCanonicalName() ).append( "\" ");
        }
		
		
		if ( field.getValue() != null ) {
            String value = String.valueOf( field.getValue() );
            if ( field.getType().equals( InputField.Type.SUBMIT ) || field.getType().equals( InputField.Type.RESET ) ) {
                value = StandardI18NFacade._( value );
            }

			builder.append("value=\"").append( value ).append("\" ");
		}
		
		Map<String, Object> params = field.getAttributes();
		for ( String key : params.keySet() ) {
			builder.append( key ).append( "=\"" )
				   .append( params.get(key) )
				   .append( "\" ");
		}
		
		builder.append("/>");
		
		String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}
		
		return data;
	}
	

}
