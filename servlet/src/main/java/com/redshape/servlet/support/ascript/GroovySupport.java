package com.redshape.servlet.support.ascript;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.ascript.evaluation.EvaluationMode;
import com.redshape.servlet.WebApplication;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.ContextId;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;
import com.redshape.utils.config.IConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/23/12
 * Time: 2:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class GroovySupport implements IResponseContext {

    public static class ContextType extends ContextId {

        protected ContextType(String contextId) {
            super(contextId);
        }

        public static final ContextType Groovy = new ContextType("ContextId.Groovy");
    }

    @Override
    public ContextId getContextType() {
        return ContextType.Groovy;
    }

    @Override
    public boolean doRedirectionHandling() {
        return false;
    }

    @Override
    public boolean doExceptionsHandling() {
        return false;
    }

    @Override
    public SupportType isSupported(IHttpRequest request) {
        return SupportType.MAY;
    }

    @Override
    public SupportType isSupported(IView view) {
        return view.getExtension().equals("groovy") ? SupportType.SHOULD : SupportType.MAY;
    }

    @Override
    public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response) throws ProcessingException {
        try {
            String[] roots = new String[] { view.getBasePath() };
            GroovyScriptEngine gse = new GroovyScriptEngine(roots);
            Binding binding = new Binding();
            binding.setVariable("request", request);
            binding.setVariable("response", response);
            binding.setVariable("view", view);
            binding.setVariable("context", WebApplication.getContext() );

            try {
                gse.run(view.getLayout().getViewPath() + "." + view.getExtension(),
                        binding);
            } catch ( Throwable e ) {
                throw new ProcessingException( e.getMessage(), e );
            }

            response.getWriter().write( (String) binding.getVariable("output") );

        } catch ( IOException e ) {
            throw new ProcessingException("IO related exception", e );
        }
    }
}
