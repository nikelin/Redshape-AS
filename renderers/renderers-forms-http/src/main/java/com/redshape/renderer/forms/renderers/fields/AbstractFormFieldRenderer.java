package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.IFormField;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormFieldRenderer<T extends IFormField> extends AbstractFormItemRenderer<T> {

    protected AbstractFormFieldRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public void repaint(T renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

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

}
