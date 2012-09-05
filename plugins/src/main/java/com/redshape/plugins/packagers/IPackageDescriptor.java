package com.redshape.plugins.packagers;

import com.redshape.plugins.meta.IPluginInfo;
import com.redshape.plugins.meta.IPublisherInfo;

import java.net.URI;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:13 PM
 */
public interface IPackageDescriptor {
    
    public List<IPackageEntry> getEntries();
    
    public boolean hasEntry( String path );
    
    public void addEntry( IPackageEntry entry );
    
    public IPackageEntry getEntry( String name );
    
    public int getEntriesCount();
    
	public PackagingType getType();

    public URI getURI();
    
    public IPackageEntry createEntry( String path, byte[] data );

}
