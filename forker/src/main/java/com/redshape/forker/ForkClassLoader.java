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

package com.redshape.forker;

import com.redshape.forker.commands.FindResourceCommand;
import com.redshape.forker.commands.FindResourcesCommand;
import com.redshape.forker.commands.ResolveClassCommand;
import com.redshape.forker.events.CommandResponseEvent;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.utils.events.IEventListener;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @package com.redshape.forker
 * @date 1/31/12 {7:40 PM}
 */
public class ForkClassLoader extends ClassLoader {

    private int resourcesCount = 0;

    protected IForkCommandExecutor executor;

    public ForkClassLoader(ClassLoader parent) {
        super(parent);
    }

    public ForkClassLoader( IForkCommandExecutor executor) {
        super();

        this.executor = executor;
    }

    public void setExecutor(IForkCommandExecutor executor) {
        this.executor = executor;
        this.executor.addEventListener(CommandResponseEvent.class, new IEventListener<CommandResponseEvent>() {
            @Override
            public void handleEvent(CommandResponseEvent event) {
                Logger.getRootLogger().info("ForkClassLoader received response: " +
                        event.getResponse().getClass().getCanonicalName() );
                if ( event.getResponse() instanceof ResolveClassCommand.Response ) {
                    ForkClassLoader.this.onResolveResponse((ResolveClassCommand.Response) event.getResponse());
                }
            }
        });
    }

    protected void onResolveResponse( ResolveClassCommand.Response response ) {
        Logger.getRootLogger().error( "Class body loaded from ResolveClassCommand.Response:"
                + response.getCanonicalName() );
        this.defineClass( response.getCanonicalName(), response.getClazzData(), 0, response.getClazzData().length );
    }

    public int getResourcesCount() {
        return resourcesCount;
    }

    @Override
    protected URL findResource(String name)  {
//        try {
//            if ( this.executor == null ) {
//                return super.findResource(name);
//            }
//
//            FindResourceCommand.Response response = this.executor.execute(new FindResourceCommand.Request(name));
//            return this.saveFile( response.getData() ).toURI().toURL();
//        } catch ( IOException e ) {
//            throw new IOError(e);
//        } catch ( ProcessException e ) {
//            throw new IOError( e );
//        }
        return super.findResource(name);
    }

    protected File saveFile( byte[] data ) throws IOException {
        File file = new File( this.generateFileName() );

        OutputStream stream = new FileOutputStream(file);
        try {
            stream.write(data);
        } finally {
            stream.close();
        }

        return file;
    }

    protected String generateFileName() {
        return "resource-" + this.getResourcesCount() + "-.bin";
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
//        try {
//            if ( this.executor == null ) {
//                return super.findResources(name);
//            }
//
//            FindResourcesCommand.Response response = this.executor.execute(new FindResourcesCommand.Request(name));
//
//            List<URL> resources = new ArrayList<URL>();
//            for (URI uri : response.getResources() ) {
//                resources.add( this.findResource(uri.toString()) );
//            }
//
//            return Collections.enumeration(resources);
//        } catch ( ProcessException e ) {
//            throw new IOException( e.getMessage(), e );
//        }
        return super.findResources(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
//        try {
//            if ( this.executor == null ) {
//                return super.findClass(name);
//            }
//
//            ResolveClassCommand.Request request = new ResolveClassCommand.Request();
//            request.setCanonicalName(name);
//
//            ResolveClassCommand.Response response = this.executor.execute(request);
//            return this.defineClass(name, response.getClazzData(), 0, response.getClazzData().length);
//        } catch ( ProcessException e ) {
//            throw new ClassNotFoundException( e.getMessage(), e );
//        }
    }

}
