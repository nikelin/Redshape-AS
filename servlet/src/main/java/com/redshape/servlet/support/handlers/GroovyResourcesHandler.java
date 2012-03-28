package com.redshape.servlet.support.handlers;

import com.redshape.utils.Commons;
import com.redshape.utils.IPackagesLoader;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.net.URI;

/**
 * @author nikelin
 * @date 1:18
 */
public class GroovyResourcesHandler implements IPackagesLoader.ResourcesHandler {

    private ClassLoader contextClassLoader;
    private CompilerConfiguration configuration;

    public GroovyResourcesHandler() {
        this( Thread.currentThread().getContextClassLoader() );
    }

    public GroovyResourcesHandler( ClassLoader parentLoader ) {
        this(parentLoader, null);
    }

    public GroovyResourcesHandler(ClassLoader contextClassLoader, CompilerConfiguration configuration) {
        Commons.checkNotNull(contextClassLoader);

        this.contextClassLoader = contextClassLoader;
        this.configuration = configuration;
    }

    protected CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    protected ClassLoader getContextClassLoader() {
        return contextClassLoader;
    }

    protected ClassLoader createClassLoader( URI[] uris ) {
        GroovyClassLoader loader;
        if ( this.getConfiguration() != null ) {
            loader = new GroovyClassLoader( this.getContextClassLoader(), this.getConfiguration() );
        } else {
            loader = new GroovyClassLoader( this.getContextClassLoader() );
        }

        for ( URI uri : uris ) {
            loader.addClasspath( uri.toString() );
        }

        return loader;
    }

    @Override
    public Class<?> handle(String className) throws ClassNotFoundException {
        return this.handle(className, new URI[] {} );
    }

    @Override
    public Class<?> handle(String className, URI[] uris) throws ClassNotFoundException {
        return this.createClassLoader(uris).loadClass(className);
    }
}
