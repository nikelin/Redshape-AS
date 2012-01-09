package com.redshape.plugins.packagers;

import com.redshape.plugins.meta.IPublisherInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
class PackageDescriptor implements IPackageDescriptor {
    private List<IPackageEntry> entries = new ArrayList<IPackageEntry>();
    private URI uri;
    private PackagingType packagingType;

    public PackageDescriptor(URI uri, PackagingType packagingType) {
        this.uri = uri;
        this.packagingType = packagingType;
    }

    @Override
    public List<IPackageEntry> getEntries() {
        return this.entries;
    }

    @Override
    public boolean hasEntry(String path) {
        for ( IPackageEntry entry : entries ) {
            if ( entry.getPath().contains(path) ) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void addEntry(IPackageEntry entry) {
        this.entries.add(entry);
    }

    @Override
    public IPackageEntry getEntry(String name) {
        for ( IPackageEntry entry : entries ) {
            if ( entry.getPath().contains(name) ) {
                return entry;
            }
        }

        return null;
    }

    @Override
    public int getEntriesCount() {
        return this.entries.size();
    }

    @Override
    public PackagingType getType() {
        return this.packagingType;
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public IPackageEntry createEntry(String path, byte[] data) {
        return new PackageEntry( path, data );
    }

}
