package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.views.IView;

/**
 * @author nikelin
 * @date 13:52
 */
public interface IContextSwitcher {

    public boolean isCompitable( IHttpRequest request, ContextId contextId );

    public IResponseContext chooseContext( ContextId contextId );

    public IResponseContext chooseContext( IHttpRequest request, IView view );

}
