package com.redshape.servlet.actions.index;

import com.redshape.servlet.core.controllers.AbstractAction;
import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.views.ViewAttributes;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Action( name = "index", controller = "index", view="index/index" )
public class IndexAction extends AbstractAction {
	
    public void process( IHttpRequest request, IHttpResponse response ) {
        this.getView().setAttribute(ViewAttributes.PAGE_TITLE, "Home");
    }

}
