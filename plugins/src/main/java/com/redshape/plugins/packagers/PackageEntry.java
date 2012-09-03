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
