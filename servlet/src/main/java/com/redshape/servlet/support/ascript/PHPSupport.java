package com.redshape.servlet.support.ascript;

import com.caucho.quercus.Quercus;
import com.caucho.vfs.Path;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.ContextId;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/23/12
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PHPSupport implements IResponseContext {

    public static class ContextType extends ContextId {

        protected ContextType(String contextId) {
            super(contextId);
        }

        public static final ContextType PHP = new ContextType("ContextId.PHP");
    }

    private Quercus quercus;

    public PHPSupport() {
        this.quercus = new Quercus() ;
        this.quercus.setPhpVersion("5.3");
        this.quercus.setLazyCompile(true);
    }

    @Override
    public ContextId getContextType() {
        return ContextType.PHP;
    }

    @Override
    public boolean doRedirectionHandling() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean doExceptionsHandling() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SupportType isSupported(IHttpRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SupportType isSupported(IView view) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response) throws ProcessingException {
    }
}
