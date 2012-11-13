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
