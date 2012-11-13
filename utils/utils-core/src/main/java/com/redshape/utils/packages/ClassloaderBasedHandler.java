/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
