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

package com.redshape.plugins.packagers.engines;

import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.packagers.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JARPackageSupport implements IPackageSupport {
    @Autowired( required = true )
    private IDescriptorsRegistry registry;

    public IDescriptorsRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(IDescriptorsRegistry registry) {
        this.registry = registry;
    }

    @Override
    public IPackageDescriptor processResource( PackagingType type, IPluginResource resource) throws PackagerException {
        JarInputStream dataStream = null;

        try {
            IPackageDescriptor descriptor = this.getRegistry().getDescriptor(resource);
            if ( descriptor != null ) {
                return descriptor;
            }

            descriptor = this.getRegistry().createDescriptor(type, resource.getURI() );

            dataStream = new JarInputStream( resource.getInputStream() );
            JarEntry entry;
            int offset = 0;
            while ( ( entry = dataStream.getNextJarEntry() ) != null ) {
                int entrySize = (int) entry.getSize();

                byte[] data = new byte[offset + entrySize];
                dataStream.read(data, offset, entrySize);
                offset += entry.getSize();

                descriptor.addEntry( descriptor.createEntry(entry.getName(), data ) );
            }

            return descriptor;
        } catch ( IOException e ) {
            throw new PackagerException("I/O related exception", e );
        } finally {
            if ( dataStream != null ) {
                try {
                    dataStream.close();
                } catch ( IOException e ) {
                    throw new IllegalStateException( "Critical I/O exception", e );
                }
            }
        }
    }

    @Override
    public boolean isSupported(PackagingType type) {
        return type.equals( PackagingType.JAR );
    }
}
