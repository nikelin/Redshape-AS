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

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class StandardPackagesRegistry implements IPackagesRegistry {

    private Map<PackagingType, IPackageSupport> registry = new HashMap<PackagingType, IPackageSupport>();

    public StandardPackagesRegistry(Map<PackagingType, IPackageSupport> registry) {
        this.registry = registry;
    }

    @Override
    /**
     * This dummy implementation ignore real resource mime-type
     * in prior to it's actual extension.
     * In a future this implementation must be replaced by most
     * correct one.
     */
    public PackagingType detectType(IPluginResource resource) {
        String[] pathParts = resource.getURI().getPath().split("\\/");
        String path = pathParts[pathParts.length - 1];
        if ( !path.contains(".") ) {
            return PackagingType.INLINE;
        }
        
        PackagingType result =  PackagingType.valueOf( path.split("\\.")[1] );
        if ( result == null ) {
            result = PackagingType.UNKNOWN;
        }
        
        return result;
    }

    @Override
    public IPackageSupport getSupport(PackagingType type) {
        return this.registry.get(type); 
    }

    @Override
    public void registerProvider(PackagingType type, IPackageSupport provider) {
        this.registry.put( type, provider );
    }
}
