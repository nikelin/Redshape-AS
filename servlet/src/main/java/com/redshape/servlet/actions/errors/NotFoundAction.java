package com.redshape.servlet.actions.errors;

import com.redshape.servlet.core.controllers.AbstractAction;
import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.ViewAttributes;

@Action( controller = "errors", name = "404", view="errors/404" )
public class NotFoundAction extends AbstractAction {

	@Override
	public void process() throws ProcessingException {
		this.getView().setAttribute( ViewAttributes.PAGE_TITLE, "404");
	}
	
}
