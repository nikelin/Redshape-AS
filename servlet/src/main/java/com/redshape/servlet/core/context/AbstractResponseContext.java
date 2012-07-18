package com.redshape.servlet.core.context;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResponseContext implements IResponseContext {

    private ContextId contextType;

    protected AbstractResponseContext(ContextId contextType) {
        this.contextType = contextType;
    }

    @Override
    public ContextId getContextType() {
        return this.contextType;
    }
}
