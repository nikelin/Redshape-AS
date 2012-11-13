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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryResource implements IPluginResource {
    private File file;

    public DirectoryResource( File file ) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public URI getURI() {
        return this.file.toURI();
    }

    @Override
    public int getSize() {
        return this.file.list().length;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException("Operation not supported");
    }

    @Override
    public OutputStream getOuputStream() throws IOException {
        throw new IOException("Operation not supported");
    }

    @Override
    public boolean canWrite() {
        return this.file.canWrite();
    }

    @Override
    public boolean canRead() {
        return this.file.canRead();
    }
}
