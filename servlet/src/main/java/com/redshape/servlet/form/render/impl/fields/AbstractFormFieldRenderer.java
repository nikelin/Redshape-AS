package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.servlet.form.render.impl.AbstractFormItemRenderer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormFieldRenderer<T extends IFormField<?>>
									extends AbstractFormItemRenderer<T>
									implements IFormFieldRenderer<T> {

    protected String applyDecorators( StringBuilder builder, IFormField field ) {
        String data = builder.toString();
		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}

        return data;
    }

}
