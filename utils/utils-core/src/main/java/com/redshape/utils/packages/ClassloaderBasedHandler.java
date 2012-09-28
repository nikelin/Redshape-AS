package com.redshape.utils.packages;

import com.redshape.utils.Commons;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.PackageLoaderException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author nikelin
 * @date 1:04
 */
public class ClassloaderBasedHandler implements IPackagesLoader.ResourcesHandler {

    private ClassLoader contextClassLoader;

    public ClassloaderBasedHandler() {
        this(null);
    }

    public ClassloaderBasedHandler( ClassLoader loader ) {
        this.contextClassLoader = loader;
    }

    protected ClassLoader getContextClassLoader() {
        return contextClassLoader;
    }

    private ClassLoader createClassLoader( URI[] targetEntries ) throws MalformedURLException {
        URL[] urls = new URL[targetEntries.length];
        for ( int i = 0 ; i < targetEntries.length; i++ ) {
            urls[i] = targetEntries[i].toURL();
        }

        return new URLClassLoader( urls, Thread.currentThread().getContextClassLoader() );
    }

    protected ClassLoader getClassLoader() {
        return Commons.select( this.contextClassLoader, Thread.currentThread().getContextClassLoader() );
    }

    @Override
    public Class<?> handle(String className) throws PackageLoaderException {
        Commons.checkNotNull(this.getClassLoader(), "Classloader not assigned");

        try {
            return this.getClassLoader().loadClass(className);
        } catch ( ClassNotFoundException e ) {
            throw new PackageLoaderException(e.getMessage(), e);
        }
    }

    @Override
    public Class<?> handle(String className, URI[] uris) throws PackageLoaderException {
        try {
            return this.createClassLoader(uris).loadClass(className);
        } catch ( MalformedURLException e ) {
            throw new IllegalArgumentException( e.getMessage(), e );
        } catch ( ClassNotFoundException e ) {
            throw new PackageLoaderException( e.getMessage(), e );
        }
    }

}
