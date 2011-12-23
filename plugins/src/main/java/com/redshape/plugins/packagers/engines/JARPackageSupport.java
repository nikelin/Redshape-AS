package com.redshape.plugins.packagers.engines;

import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.packagers.*;

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
    private IPluginResource resource;
    private JarInputStream dataStream;

    public JARPackageSupport( IPluginResource resource ) throws PackagerException {
        this.resource = resource;
        this.init();
    }

    protected void init() throws PackagerException {
        try {
            this.dataStream = new JarInputStream( resource.getInputStream() );
        } catch ( IOException e ) {
            throw new PackagerException("I/O related exception", e );
        }
    }

    @Override
    public List<IPackageEntry> getEntries() throws IOException {
        List<IPackageEntry> entries = new ArrayList<IPackageEntry>();
        synchronized (this.dataStream) {
            try {
                this.dataStream.reset();

                JarEntry entry;
                while ( null != ( entry = this.dataStream.getNextJarEntry() ) ) {
                    entries.add( this.createPackageEntry(entry) );
                }
            } finally {
                this.dataStream.reset();
            }
        }

        return entries;
    }

    @Override
    public IPackageEntry getEntry(String path) throws IOException {
        synchronized (this.dataStream ) {
            try {
                this.dataStream.reset();
                IPackageEntry result = null;
                JarEntry entry;
                while ( null != ( entry = this.dataStream.getNextJarEntry()  ) ) {
                    if ( entry.getName().equals(path) ) {
                        result = this.createPackageEntry(entry);
                        break;
                    }
                }

                return result;
            } finally {
                this.dataStream.reset();
            }
        }
    }

    protected IPackageEntry createPackageEntry( JarEntry entry ) throws IOException {
        byte[] data = new byte[(int)entry.getSize()];
        this.dataStream.read( data, 0 , (int) entry.getSize() );

        return new PackageEntry( entry.getName(), data );
    }

    @Override
    public boolean isSupported(PackagingType type) {
        return type.equals( PackagingType.JAR );
    }
}
