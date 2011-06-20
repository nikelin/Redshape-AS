package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;

/**
 * @author nikelin
 * @date 13:52
 */
public interface IContextSwitcher {

    public IResponseContext chooseContext( IHttpRequest request );

}
