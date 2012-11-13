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

package com.redshape.plugins.loaders.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginResource implements IPluginResource {
    private URI uri;
    private int size;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean readable;
    private boolean writable;
    
    public PluginResource( URI uri, int size, InputStream stream ) {
        this(uri, size, stream, null);
    }
    
    public PluginResource( URI uri, int size, OutputStream stream ) {
        this(uri, size, null, stream);
    }
    
    public PluginResource( URI uri, int size, InputStream inputStream, OutputStream outputStream ) {
        super();

        this.size = size;
        this.uri = uri;
        this.inputStream = inputStream;
        this.readable = this.inputStream != null;
        this.outputStream = outputStream;
        this.writable = this.outputStream != null;
    }
    
    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.inputStream;
    }

    @Override
    public OutputStream getOuputStream() throws IOException {
        return this.outputStream;
    }

    @Override
    public boolean canWrite() {
        return this.writable;
    }

    @Override
    public boolean canRead() {
        return this.readable;
    }
}
