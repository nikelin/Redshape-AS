package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.render.IFormFieldRenderer;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormFieldRenderer<T extends IFormField<?>> implements IFormFieldRenderer<T> {

    protected String applyDecorators( StringBuilder builder, IFormField field ) {
        String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}

        return data;
    }

    protected void buildAttributes( StringBuilder builder, IFormField field ) {
        Map<String, Object> attributes = field.getAttributes();
		for ( String key : attributes.keySet() ) {
            Object value = attributes.get(key);
            if ( value == null ) {
                continue;
            }

			builder.append( key ).append("=\"").append( attributes.get(key) ).append("\" ");
		}
    }
}
