package com.redshape.renderer.forms.renderers.decorators;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IForm;
import com.redshape.form.IFormItem;

public class LegendDecorator extends AbstractDecorator {

	@Override
	public Widget decorate(IFormItem item, Widget data) {
		if ( ! ( item instanceof IForm ) ) {
			return data;
		}

        CaptionPanel wrapper = new CaptionPanel();

		if ( item instanceof IForm && ( (IForm) item ).getContext() != null ) {
			this.buildAttributes( item.getAttributes(), wrapper );
		}

        wrapper.setCaptionText( ( (IForm) item).getLegend() );
		wrapper.add(data);
		
		return wrapper;
	}
}
