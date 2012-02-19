package com.redshape.renderer.forms.renderers.decorators;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;

public class FormFieldDecorator extends AbstractDecorator {

	@Override
	public Widget decorate( IFormItem item, Widget data ) {
		if ( !( item instanceof IFormField ) ) {
			return data;
		}


        HorizontalPanel field = new HorizontalPanel();
        field.add( data );
        return field;
	}

}
