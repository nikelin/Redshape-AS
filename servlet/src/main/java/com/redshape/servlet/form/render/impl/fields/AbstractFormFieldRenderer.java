package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.servlet.form.render.impl.AbstractFormItemRenderer;
import com.redshape.validators.result.IValidationResult;

import java.util.Map;

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

	protected void applyErrorStateIfNeeds( IFormField<?> field ) {
		Map<String, Object> params = field.getAttributes();
		if ( !field.getValidationResults().isEmpty() ) {
			for ( IValidationResult result : field.getValidationResults() ) {
				if ( !result.isValid() ) {
					String classValue = (String) params.get("class");
					if ( classValue == null ) {
						params.put("class", "error");
					} else if ( !classValue.contains(" error") ) {
						params.put("class", params.get("class") + " error" );
					}
					break;
				}
			}
		}
	}

    protected String applyDecorators( StringBuilder builder, IFormField field, RenderMode mode) {
        String data = builder.toString();
		if ( mode.equals(RenderMode.WITHOUT_DECORATORS) ) {
			return data;
		}

		for ( IDecorator decorator : field.getDecorators() ) {
			data = decorator.decorate(field, data);
		}

        return data;
    }

}
