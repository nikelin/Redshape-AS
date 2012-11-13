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

package com.redshape.plugins.packagers;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
class PackageEntry implements IPackageEntry {
    private IPackageDescriptor descriptor;
    private String path;
    private int size;
    private byte[] data;
    private String mimeType;
    
    PackageEntry( IPackageDescriptor descriptor, String path, byte[] data ) {
        this.descriptor = descriptor;
        this.path = path;
        this.data = data;
        if ( data != null ) {
            this.size = data.length;
        }
    }

    @Override
    public IPackageDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public String getPath() {
        return this.path;
    }
    
    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public byte[] getData() {
        return this.data;
    }
    
    public void setMimeType( String mimeType ) {
        this.mimeType = mimeType;
    }

    @Override
    public String getMimeType() {
        return this.mimeType;
    }

}
