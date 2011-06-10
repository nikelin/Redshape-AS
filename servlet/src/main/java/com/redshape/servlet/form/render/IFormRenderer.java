package com.redshape.servlet.form.render;

import com.redshape.servlet.form.IForm;

public interface IFormRenderer extends IFormItemRenderer<IForm> {

	@Override
	public String render( IForm form );
	
}
