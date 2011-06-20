package com.redshape.servlet.form.render.impl.fields;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.LabelField;
import com.redshape.servlet.form.render.IFormFieldRenderer;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 16.06.11
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class LabelFieldRenderer implements IFormFieldRenderer<LabelField> {

	@Override
	public String render(LabelField field, RenderMode mode ) {
		StringBuilder builder = new StringBuilder();
		builder.append("<label for=\"")
			   .append( field.getName() )
               .append("\" ");

		if ( field.getId() != null ) {
			builder.append("id=\"").append( field.getId() ).append( "\" ");
		}

        builder.append(">");

		if ( field.getValue() != null ) {
			builder.append( StandardI18NFacade._( field.getValue() ) );
		}

        builder.append("</label>");

		String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}

		return data;
	}


}