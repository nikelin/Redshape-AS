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

package com.redshape.plugins.meta;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public interface IMetaLoadersRegistry {

    public boolean isSupported( IPackageDescriptor descriptor );

    public void registerLoader( IMetaLoader descriptor );

    public IMetaLoader[] list();
    
    public IMetaLoader selectLoader( IPackageDescriptor descriptor ) throws LoaderException;

}
