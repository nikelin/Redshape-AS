package com.redshape.plugins.packagers.engines;

import com.redshape.plugins.loaders.resources.DirectoryResource;
import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.packagers.*;
import com.redshape.utils.IResourcesLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */
public class InlinePackageSupport implements IPackageSupport {

    @Autowired( required = true )
    private IDescriptorsRegistry descriptorsRegistry;

    @Autowired( required = true )
    private IResourcesLoader loader;

    public IResourcesLoader getLoader() {
        return loader;
    }

    public void setLoader(IResourcesLoader loader) {
        this.loader = loader;
    }

    public IDescriptorsRegistry getDescriptorsRegistry() {
        return descriptorsRegistry;
    }

    public void setDescriptorsRegistry(IDescriptorsRegistry descriptorsRegistry) {
        this.descriptorsRegistry = descriptorsRegistry;
    }

    @Override
    public IPackageDescriptor processResource(PackagingType type, IPluginResource resource) throws PackagerException {
        try {
            if ( !( resource instanceof DirectoryResource ) ) {
                throw new PackagerException("Resource must be instance of DirectoryResource type");
            }
    
            IPackageDescriptor descriptor = this.getDescriptorsRegistry().getDescriptor(resource);
            if ( descriptor != null ) {
                return descriptor;
            }
            
            descriptor = this.getDescriptorsRegistry().createDescriptor(type, resource.getURI() );
            
            for ( IPackageEntry entry : this.createEntriesList( descriptor, ((DirectoryResource) resource).getFile() ) ) {
                descriptor.addEntry( entry );
            }
            
            return descriptor;
        } catch ( IOException e ) {
            throw new PackagerException( "I/O related exception", e );
        }
    }
    
    protected List<IPackageEntry> createEntriesList( IPackageDescriptor descriptor, File file )
            throws IOException {
        List<IPackageEntry> entries = new ArrayList<IPackageEntry>();
        for ( File fileChild : file.listFiles() ) {
            entries.add( this.createEntry(descriptor, fileChild) );
            
            if ( fileChild.isDirectory() ) {
                entries.addAll( this.createEntriesList(descriptor, fileChild) );
            }
        }
        
        return entries;
    }
    
    protected IPackageEntry createEntry( IPackageDescriptor descriptor, File node )
            throws IOException {
        return descriptor.createEntry( node.getPath(), node.isFile() ? this.readData(node) : null );
    }
    
    protected byte[] readData( File node ) throws IOException {
        byte[] buff = new byte[(int) node.length()];
        FileInputStream inputStream = new FileInputStream(node);
        inputStream.read( buff );
        return buff;
    }

    @Override
    public boolean isSupported(PackagingType type) {
        return PackagingType.INLINE.equals(type);
    }
}
