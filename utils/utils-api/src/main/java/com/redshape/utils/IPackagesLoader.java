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

package com.redshape.utils;

import java.net.URI;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 8/13/11 12:27 PM
 */
public interface IPackagesLoader {

    public interface ResourcesHandler {

        public Class<?> handle( String className ) throws PackageLoaderException;

        public Class<?> handle( String className, URI[] uris ) throws PackageLoaderException;

    }

    public void setDefaultResourcesHandler( ResourcesHandler handler );

    public void registerResourcesHandler( String pattern, ResourcesHandler handler );

	public <T> Class<T>[] getClasses(String pkgName) throws PackageLoaderException;

	public <T> Class<T>[] getClasses(String pkgName, IFilter<Class<T>> filter) throws PackageLoaderException;

}
