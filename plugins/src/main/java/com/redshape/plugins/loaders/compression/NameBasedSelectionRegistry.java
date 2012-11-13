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

package com.redshape.plugins.loaders.compression;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class NameBasedSelectionRegistry implements ICompressionMethodsRegistry {
    private Map<String, ICompressionSupport> engines = new HashMap<String, ICompressionSupport>();

    @Override
    public ICompressionSupport select(IPluginResource resource) {
        String[] pathParts = resource.getURI().getPath().split("\\/");
        if ( pathParts.length < 1 ) {
            return null;
        }
        
        String[] nameParts = pathParts[pathParts.length-1].split("\\.");
        if ( nameParts.length != 2 ) {
            return null;
        }
        
        return this.engines.get( nameParts[1] );
    }
}
