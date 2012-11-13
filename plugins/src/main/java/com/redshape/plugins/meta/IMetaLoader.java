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
import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 4:43 PM
 */
public interface IMetaLoader {

	public IPluginInfo load( IPackageDescriptor descriptor ) throws LoaderException;

    public boolean isSupports( IPackageDescriptor descriptor );

}
